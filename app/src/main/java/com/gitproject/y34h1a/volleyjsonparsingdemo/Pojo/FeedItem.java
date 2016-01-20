package com.gitproject.y34h1a.volleyjsonparsingdemo.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedItem implements Parcelable {
    private String id;
    private String author, postTitle, timeStamp, url, profilePic, thumnail, detailPost;

    public FeedItem() {
    }

    // CONSTRUCTOR
    public FeedItem(String id, String name, String status, String timeStamp, String url, String profilePic, String thumnail) {
        super();
        this.id = id;
        this.author = name;
        this.postTitle = status;
        this.timeStamp = timeStamp;
        this.url = url;
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

    //URL
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    //Detail Post
    public String getDetailPost() {
        return detailPost;
    }

    public void setDetailPost(String detailPost) {
        this.detailPost = detailPost;
    }

    // Post Title
    public String getPostTitle() {
        return postTitle;
    }

    //
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    /*******************************************************************************************
     *                                       PARCEBALE FOR SAVING DATA
     ********************************************************************************************/

    // For saving data so that json data never gone if i don't kill the applicaiton
    protected FeedItem(Parcel in) {
        id = in.readString();
        author = in.readString();
        postTitle = in.readString();
        timeStamp = in.readString();
        url = in.readString();
        profilePic = in.readString();
        thumnail = in.readString();
        detailPost = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(postTitle);
        dest.writeString(timeStamp);
        dest.writeString(url);
        dest.writeString(profilePic);
        dest.writeString(thumnail);
        dest.writeString(detailPost);
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel in) {
            return new FeedItem(in);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
