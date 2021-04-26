package com.palo.palo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Model for posts.
 */
public class Palo implements Parcelable {
    private User author;
    private String postDate, caption;
    private Attachment attachment;
    private Boolean isLiked;
    int id;
    private String likeStr;

    public Palo(){
        isLiked = false; //todo remove this
    }

    public Palo(Parcel parcel){
        author = parcel.readParcelable(User.class.getClassLoader());
        caption = parcel.readString();
        postDate = parcel.readString();
        attachment = parcel.readParcelable(Attachment.class.getClassLoader());
        id = parcel.readInt();

        likeStr = parcel.readString();
        if(likeStr.equals("true"))
            isLiked = true;
        if(likeStr.equals("false"))
            isLiked = false;
    }

    public Palo(JSONObject paloJSON) throws JSONException {
        id = paloJSON.getInt("id");
        author = new User(paloJSON.getInt("user_id"));
        postDate = paloJSON.getString("createDate");
        caption = paloJSON.getString("description");
        switch (paloJSON.getInt("type")){
            case 0:
                attachment = new Album(paloJSON.getString("spot_id"));
                break;
            case 1:
                attachment = new Artist(paloJSON.getString("spot_id"));
                break;
            case 2:
                attachment = new Song(paloJSON.getString("spot_id"));
                break;
            default: System.out.println("error");
        }
        isLiked = false;
    }

    public Palo(JSONObject paloJSON, int currentUser) throws JSONException {
        id = paloJSON.getInt("id");
        author = new User(paloJSON.getInt("user_id"));
        postDate = paloJSON.getString("createDate");
        caption = paloJSON.getString("description");
        switch (paloJSON.getInt("type")){
            case 0:
                attachment = new Album(paloJSON.getString("spot_id"));
                break;
            case 1:
                attachment = new Artist(paloJSON.getString("spot_id"));
                break;
            case 2:
                attachment = new Song(paloJSON.getString("spot_id"));
                break;
            default: System.out.println("error");
        }
        JSONArray likeList = paloJSON.getJSONArray("likeList");
        for(int i = 0; i < likeList.length(); i++){
            JSONObject object = likeList.getJSONObject(i);
            if(object.getInt("user_id") == currentUser)
                isLiked = true;
        }
        isLiked = false;
    }

    public Palo(JSONObject paloJSON, String username, String profilePic) throws JSONException {
        System.out.println("!!!!" + paloJSON.toString());
        id = paloJSON.getInt("id");
        author = new User(paloJSON.getInt("user_id"), username);
        //author = new User();
        author.setUsername(username);
        author.setProfileImage(profilePic);
        postDate = paloJSON.getString("createDate");
        caption = paloJSON.getString("description");
        //postDate = paloJSON.getString("createDate");
        //caption = paloJSON.getString("caption");
        switch (paloJSON.getInt("type")){
            case 0:
                attachment = new Album(paloJSON.getString("spot_id"));
                break;
            case 1:
                attachment = new Artist(paloJSON.getString("spot_id"));
                break;
            case 2:
                attachment = new Song(paloJSON.getString("spot_id"));
                break;
            default: System.out.println("error");
        }

        //attachment = new Song("7niXXokVzkvRw81pF4Q9Ad"); //todo delete
        
        isLiked = false;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
    public void setAuthorUsername(String username) {
        this.author.setUsername(username);
    }

    public void setPaloAuthorProfileImage(String image) { this.author.setProfileImage(image);}

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean getIsLiked() {
        return this.isLiked;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    
    public boolean toggleIsLiked(){
        this.isLiked = ! this.isLiked;
        return isLiked;
    }

    public String getProfileImage() {
        return this.author.getProfileImage();
    }

    public String getAuthorUsername() {
        return this.author.getUsername();
    }

    public void updateAttachment(String name, String artist, String albumCover, String id ){
        attachment.setTitle(name);
        attachment.setArtist(artist);
        attachment.setAlbumCover(albumCover);
        attachment.setSpotifyId(id);
    }
    
    @Override
    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(author, flags);
        dest.writeString(caption);
        dest.writeString(postDate);
        dest.writeParcelable(attachment, flags);
        dest.writeInt(id);
        dest.writeString(isLiked ? "true" : "false");
    }

    public static final Parcelable.Creator<Palo> CREATOR = new Parcelable.Creator<Palo>() {

        @Override
        public Palo createFromParcel(Parcel parcel) {
            return new Palo(parcel);
        }

        @Override
        public Palo[] newArray(int size) {
            return new Palo[0];
        }
    };

    public void updateAttachmentPlaybackLink(String playbackLink) {
        ((Song)attachment).setPlaybackLink(playbackLink);
    }
}

