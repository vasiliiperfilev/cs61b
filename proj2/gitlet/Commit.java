package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;

public class Commit implements Serializable {
    /** Static methods */

    public static Commit readCurrentCommit() {
        if (Repository.currentBranch != null) {
            File commitFile = Utils.join(Repository.COMMITS_DIR, Repository.currentBranch.getLastCommitId());
            return fromFile(commitFile);
        }
        return null;
    }

    public static Commit fromFile(File commitFile) {
        if (commitFile.exists()) {
            return Utils.readObject(commitFile, Commit.class);
        }
        return null;
    }

    /** Fields */
    private String message;
    private Date timestamp;
    private String parentCommitId;
    private HashMap<String, String> trackedBlobs;
    private String id;

    /** Getters/Setters */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getParentCommitId() {
        return parentCommitId;
    }

    public void setParentCommitId(String parentCommitId) {
        this.parentCommitId = parentCommitId;
    }

    public HashMap<String, String> getTrackedBlobs() {
        return trackedBlobs;
    }

    public void setTrackedBlobs(HashMap<String, String> trackedBlobs) {
        this.trackedBlobs = trackedBlobs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** Constructors */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.parentCommitId = null;
        this.trackedBlobs = new HashMap<String, String>();
        this.id = generateId();
    }

    public Commit(StagingArea stagingArea, Commit parentCommit, String message) {
        this.parentCommitId = parentCommit.getId();
        this.message = message;
        this.timestamp = new Date();
        this.trackedBlobs = new HashMap<>(parentCommit.getTrackedBlobs());
        updateTrackedBlobs(stagingArea);
        this.id = generateId();
    }


    /** Methods */
    private String generateId() {
        return Utils.sha1(Utils.serialize(this));
    }

    public void save() {
        File commitFile = Utils.join(Repository.COMMITS_DIR, id);
        try {
            commitFile.createNewFile();
            Utils.writeObject(commitFile, this);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public boolean containsSame(Blob newBlob) {
        String newFileName = newBlob.getFileName();
        String newId = newBlob.getBlobId();
        String blobId = trackedBlobs.get(newFileName);
        return blobId != null && blobId.equals(newId);
    }

    private void updateTrackedBlobs(StagingArea stagingArea) {
        HashMap<String, String> stagedAdd = stagingArea.getAddBlobs();
        HashMap<String, String> stagedRemove = stagingArea.getRemoveBlobs();
        for (String fileName : stagedAdd.keySet()) {
            if (trackedBlobs.containsKey(fileName)) {
                trackedBlobs.replace(fileName, stagedAdd.get(fileName));
            } else {
                trackedBlobs.put(fileName, stagedAdd.get(fileName));
            }
        }
        for (String fileName : stagedRemove.keySet()) {
            if (trackedBlobs.containsKey(fileName)) {
                trackedBlobs.remove(fileName);
            }
        }
    }
}
