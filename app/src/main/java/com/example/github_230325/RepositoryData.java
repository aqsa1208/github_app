package com.example.github_230325;

public class RepositoryData {

    private String ownerAvatar_url;
    private String repoUrl;
    private String repoName;
    private String repoDesc;
    private String repoOwner;
    private String repoStars;

    private final String repoNameStart = "Name: ";
    private final String repoDescStart = "Description: ";
    private final String repoOwnerStart = "Owner: ";
    private final String repoStarsStart = "Stars Number:";

    public RepositoryData(String ownerAvatar_url,String repoUrl, String repoName, String repoDesc, String repoOwner, String repoStars) {
        this.ownerAvatar_url = ownerAvatar_url;
        this.repoUrl = repoUrl;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.repoOwner = repoOwner;
        this.repoStars = repoStars;
    }

    public String getOwnerAvatar_url() {
        return ownerAvatar_url;
    }

    public String getrepoUrl() {
        return repoUrl;
    }


    public String getRepoName() {
        return this.repoNameStart + repoName;
    }


    public String getRepoDesc() {
        return this.repoDescStart + repoDesc;
    }


    public String getRepoOwner() {
        return this.repoOwnerStart + repoOwner;
    }


    public String getRepoStars() {
        return this.repoStarsStart + repoStars;
    }


}
