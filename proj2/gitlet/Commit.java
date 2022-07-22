package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.List;

public class Commit implements Serializable {
    /** Static methods */

    public static Commit fromFile(String commitId) {
        String fullCommitId = getFullCommitId(commitId);
        File commitFile = Utils.join(Repository.COMMITS_DIR, fullCommitId);
        if (commitFile.exists()) {
            return Utils.readObject(commitFile, Commit.class);
        }
        System.out.println("No commit with that id exists.");
        System.exit(0);
        return null;
    }

    public static String readFileContentFrom(String commitId, String fileName) {
        Commit commit = fromFile(commitId);
        String blobId = commit.getTrackedBlobs().get(fileName);
        if (blobId != null) {
            Blob blob = Blob.fromFile(blobId);
            String fileContent = blob.getFileContent();
            return fileContent;
        }
        System.out.println("File does not exist in that commit.");
        System.exit(0);
        return null;
    }

    public static String getFullCommitId(String startId) {
        String fullCommitId = null;
        if (startId.length() < 40) {
            List<String> commitsList = Utils.plainFilenamesIn(Repository.COMMITS_DIR);
            for (String id : commitsList) {
                String shortId = id.substring(0, startId.length());
                if (shortId.equals(startId)) {
                    fullCommitId = id;
                }
            }
        } else {
            fullCommitId = startId;
        }
        return fullCommitId;
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

    public String log() {
        Commit commitToLog = this;
        String log = "";
        while (commitToLog != null) {
            log += commitToLog.toString() + "\n";
            if (commitToLog.getParentCommitId() != null) {
                commitToLog = Commit.fromFile(commitToLog.getParentCommitId());
            } else {
                commitToLog = null;
            }
        }
        return log;
    }

    @Override
    public String toString() {
        return "===\n" +
                "commit " + id + "\n" +
                "Date: " + String.format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", timestamp) + "\n" +
                message + "\n";
    }
}
