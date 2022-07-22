package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Repository {
    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File HEAD_POINTER = Utils.join(GITLET_DIR, "HEAD");
    public static final File BRANCHES_HEAD_DIR= Utils.join(GITLET_DIR, "refs", "heads");
    public static final File STAGING_AREA = Utils.join(GITLET_DIR, "STAGING");
    public static final File COMMITS_DIR = Utils.join(GITLET_DIR, "objects", "commits");
    public static final File BLOBS_DIR = Utils.join(GITLET_DIR, "objects", "blobs");
    public static Branch currentBranch = Branch.readCurrentBranch();
    public static StagingArea stagingArea = StagingArea.readCurrentStagingArea();

    public static void setupPersistence() {
        if (!GITLET_DIR.exists()) GITLET_DIR.mkdir();
        if (!BRANCHES_HEAD_DIR.exists()) BRANCHES_HEAD_DIR.mkdirs();
        if (!COMMITS_DIR.exists()) COMMITS_DIR.mkdirs();
        if (!BLOBS_DIR.exists()) BLOBS_DIR.mkdirs();
        try {
            if (!HEAD_POINTER.exists()) HEAD_POINTER.createNewFile();
            if (!STAGING_AREA.exists()) {
                STAGING_AREA.createNewFile();
                StagingArea stagingArea = new StagingArea();
                Utils.writeObject(STAGING_AREA, stagingArea);
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void changeHead(Branch branch) {
        Utils.writeContents(HEAD_POINTER, branch.getName());
    }

    public static void saveOrUpdateInCWD(String fileName, String fileContent) {
        File file = Utils.join(CWD, fileName);
        Utils.writeContents(file, fileContent);
    }

    public static void init() {
        Commit initialCommit = new Commit();
        initialCommit.save();
        Branch master = new Branch("master", initialCommit.getId());
        master.save();
        changeHead(master);
    }

    public static void add(String fileName) {
        Blob newBlob = new Blob(fileName);
        stagingArea.update(newBlob);
        stagingArea.save();
    }

    public static void commit(String message) {
        Commit newCommit = new Commit(stagingArea, currentBranch.getLastCommit(), message);
        newCommit.save();
        stagingArea.clear();
        currentBranch.setLastCommitId(newCommit.getId());
        saveState();
    }

    /* TODO: fill in the rest of this class. */
    static private void saveState() {
        stagingArea.save();
        currentBranch.save();
    }

    public static void checkout(String fileName) {
        String fileContent = Commit.readFileContentFrom(currentBranch.getLastCommitId(), fileName);
        saveOrUpdateInCWD(fileName, fileContent);
    }

    public static void checkout(String commitId, String fileName) {
        // read file from commit
        String fileContent = Commit.readFileContentFrom(commitId, fileName);
        // save this file in CWD (overwrite if required)
        saveOrUpdateInCWD(fileName, fileContent);
    }


    public static void log() {
        String log = currentBranch.getLastCommit().log();
        System.out.print(log);
    }
}
