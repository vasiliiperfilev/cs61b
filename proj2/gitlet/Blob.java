package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    /** Static methods */
    public static Blob fromFile(String blobId) {
        File blobFile = Utils.join(Repository.BLOBS_DIR, blobId);
        if (blobFile.exists()) {
            return Utils.readObject(blobFile, Blob.class);
        }
        return null;
    }
    public static void deleteBlobFile(String blobId) {
        File file = Utils.join(Repository.BLOBS_DIR, blobId);
        if (file.exists()) {
            file.delete();
        }
    }

    /** Fields */
    private String fileName;
    private String blobId;
    private String fileContent;

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

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    /** Constructors */
    public Blob(String fileName) {
        this.fileName = fileName;
        this.fileContent = Utils.readContentsAsString(Utils.join(Repository.CWD, this.fileName));
        this.blobId = generateId();
    }

    /** Methods */
    private String generateId() {
        File file = Utils.join(Repository.CWD, this.fileName);
        if (file.exists()) {
            return Utils.sha1(this.fileName, Utils.readContents(file));
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
