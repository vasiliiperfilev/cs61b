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

    public static Branch createNew(String name, String lastCommitId) {
        Branch branch = new Branch(name, lastCommitId);
        branch.createFile();
        return branch;
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
    private File file;

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
        this.file = Utils.join(Repository.BRANCHES_HEAD_DIR, this.name);
    }

    /** Methods */
    public void save() {
        Utils.writeContents(this.file, this.lastCommitId);
    }

    public void createFile() {
        try {
            if (this.file.exists()) {
                throw new RuntimeException();
            }
            this.file.createNewFile();
            Utils.writeContents(this.file, this.lastCommitId);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public Commit getLastCommit() {
        return Commit.fromFile(lastCommitId);
    }
}
