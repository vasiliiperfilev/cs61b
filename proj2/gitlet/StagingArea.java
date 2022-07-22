package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class StagingArea implements Serializable {
    /** Static methods */
    public static StagingArea readCurrentStagingArea() {
        if (Repository.STAGING_AREA.exists()) {
            return Utils.readObject(Repository.STAGING_AREA, StagingArea.class);
        }
        return null;
    }

    /** Fields */
    private HashMap<String, String> addBlobs;
    private HashMap<String, String> removeBlobs;

    /** Getters/Setters */
    public HashMap<String, String> getAddBlobs() {
        return addBlobs;
    }

    public void setAddBlobs(HashMap<String, String> addBlobs) {
        this.addBlobs = addBlobs;
    }

    public HashMap<String, String> getRemoveBlobs() {
        return removeBlobs;
    }

    public void setRemoveBlobs(HashMap<String, String> removeBlobs) {
        this.removeBlobs = removeBlobs;
    }

    /** Constructors */
    public StagingArea() {
        this.addBlobs = new HashMap<String, String>();
        this.removeBlobs = new HashMap<String, String>();
    }

    /** Methods */
    private void removeFromAdd(String fileName) {
        if (addBlobs.containsKey(fileName)) {
            addBlobs.remove(fileName);
            Blob.deleteBlobFile(fileName);
        };
    }

    public void addOrUpdate(Blob newBlob) {
        String fileName = newBlob.getFileName();
        String blobId = newBlob.getBlobId();
        if (Repository.currentBranch.getLastCommit().containsSame(newBlob)) {
            removeFromAdd(fileName);
            return;
        }

        if (removeBlobs.containsKey(fileName)) {
            removeBlobs.remove(fileName);
        }

        if (addBlobs.get(fileName) != null && addBlobs.get(fileName).equals(blobId)) {
            return;
        } else if (addBlobs.containsKey(fileName)) {
            Blob.deleteBlobFile(addBlobs.get(fileName));
            addBlobs.put(fileName, blobId);
            newBlob.save();
        } else {
            addBlobs.put(fileName, blobId);
            newBlob.save();
        }
    }

    public void save() {
        Utils.writeObject(Repository.STAGING_AREA, this);
    }

    public boolean isEmpty() {
        return addBlobs.isEmpty() && removeBlobs.isEmpty();
    }

    public void clear() {
        this.addBlobs = new HashMap<String, String>();
        this.removeBlobs = new HashMap<String, String>();
    }

    public void remove(String fileName) {
        removeFromAdd(fileName);
        String commitedBlobId = Repository.currentBranch.getLastCommit().getBlobId(fileName);
        if (commitedBlobId != null) {
            removeBlobs.put(fileName, commitedBlobId);
            Utils.restrictedDelete(Utils.join(Repository.CWD, fileName));
        }
    }
}
