package org.ldd.ssm.crm.domain;

public class UserCommitBlob {
    private Long id;

    private String uuid;

    private byte[] blobImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public byte[] getBlobImg() {
        return blobImg;
    }

    public void setBlobImg(byte[] blobImg) {
        this.blobImg = blobImg;
    }
}