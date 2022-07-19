package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.readContentsAsString;

public class Branch {
    /** Static methods */
    public static Branch readCurrentBranch() {
        if (Repository.HEAD_POINTER.exists()) {
            File branchFile = Utils.join(Repository.BRANCHES_HEAD_DIR, readContentsAsString(Repository.HEAD_POINTER));
            return fromFile(branchFile);
        }
        return null;
    }

    public static Branch fromFile(File branchFile) {
        if (branchFile.exists()) {
            String lastCommit = Utils.readContentsAsString(branchFile);
            return new Branch(branchFile.getName(), lastCommit);
        }
        return null;
    }

    /** Fields */
    private String name;
    private String lastCommitId;

    /** Getters/Setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCommitId() {
        return lastCommitId;
    }

    public void setLastCommitId(String lastCommitId) {
        this.lastCommitId = lastCommitId;
    }

    /** Constructors */
    public Branch(String name, String lastCommitId) {
        this.name = name;
        this.lastCommitId = lastCommitId;
    }

    /** Methods */
    public void save() {
        try {
            File branchFile = Utils.join(Repository.BRANCHES_HEAD_DIR, this.name);
            branchFile.createNewFile();
            Utils.writeContents(branchFile, this.lastCommitId);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public Commit getLastCommit() {
        File commitFile = Utils.join(Repository.COMMITS_DIR, lastCommitId);
        return Utils.readObject(commitFile, Commit.class);
    }
}
