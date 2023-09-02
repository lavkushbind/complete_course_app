package com.example.advncd;
public class Users {
    private String name;
    private String email;
    private String userID;
    private String pass;
    private String coverpic;
    private  String profilepic;
    private  String bio;
    private  String profesion;
    private int followercount;

    public int getFollowercount() {
        return followercount;
    }

    public Users(int followercount) {
        this.followercount = followercount;
    }

    public void setFollowercount(int followercount) {
        this.followercount = followercount;
    }

    public String uid;

    public Users() {
    }


    public Users(String name, String email, String userID, String pass, String coverpic, String profilepic, String bio, String profesion, int followercount, String uid) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.pass = pass;
        this.coverpic = coverpic;
        this.profilepic = profilepic;
        this.bio = bio;
        this.profesion = profesion;
        this.uid = uid;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCoverpic() {
        return coverpic;
    }

    public void setCoverpic(String coverpic) {
        this.coverpic = coverpic;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
