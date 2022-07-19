package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    /** Static methods */
    public static void deleteBlobFile(String fileName) {
        File file = Utils.join(Repository.BLOBS_DIR, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /** Fields */
    private String fileName;
    private String blobId;

    /** Getters/Setters */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    /** Constructors */
    public Blob(String fileName) {
        this.fileName = fileName;
        generateId();
    }

    /** Methods */
    public void generateId() {
        File file = Utils.join(Repository.CWD, this.fileName);
        if (file.exists()) {
            this.blobId = Utils.sha1(this.fileName, Utils.readContents(file));
        } else {
            throw new RuntimeException("File does not exist.");
        }
    }

    public void save() {
        File blobFile = Utils.join(Repository.BLOBS_DIR, blobId);
        try {
            blobFile.createNewFile();
            Utils.writeObject(blobFile, this);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
