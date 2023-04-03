package com.example.github_230325;

public class RepositoryData {

    private String ownerAvatar_url;
    private String repoName;
    private String repoDesc;
    private String repoOwner;
    private String repoStars;

    private final String repoNameStart = "Name: ";
    private final String repoDescStart = "Description: ";
    private final String repoOwnerStart = "Owner: ";
    private final String repoStarsStart = "Stars Number:";

    public RepositoryData(String ownerAvatar_url, String repoName, String repoDesc, String repoOwner, String repoStars) {
        this.ownerAvatar_url = ownerAvatar_url;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.repoOwner = repoOwner;
        this.repoStars = repoStars;
    }

    public String getOwnerAvatar_url() {
        return ownerAvatar_url;
    }

    public void setOwnerAvatar_url(String ownerAvatar_url) {
        this.ownerAvatar_url = ownerAvatar_url;
    }

    public String getRepoName() {
        return this.repoNameStart + repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDesc() {
        return this.repoDescStart + repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getRepoOwner() {
        return this.repoOwnerStart + repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public String getRepoStars() {
        return this.repoStarsStart + repoStars;
    }

    public void setRepoStars(String repoStars) {
        this.repoStars = repoStars;
    }

}
