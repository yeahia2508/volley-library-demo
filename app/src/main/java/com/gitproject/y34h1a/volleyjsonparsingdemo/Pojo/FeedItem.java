package com.gitproject.y34h1a.volleyjsonparsingdemo.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedItem {
    private String id;
    private String author, postTitle, timeStamp,profilePic, thumnail;

    public FeedItem() {
    }

    // CONSTRUCTOR
    public FeedItem(String id, String name, String status, String timeStamp, String url, String profilePic, String thumnail) {
        super();
        this.id = id;
        this.author = name;
        this.postTitle = status;
        this.timeStamp = timeStamp;
        this.profilePic = profilePic;
        this.thumnail = thumnail;
    }


    /*******************************************************************************************
    *                                        GETTER AND SETTERS
    ********************************************************************************************/

    // ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Author
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    //Status
    public String getStatus() {
        return postTitle;
    }

    public void setStatus(String status) {
        this.postTitle = status;
    }

    //Time
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    //Profile Pic
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    //Thumbernail
    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }



}
