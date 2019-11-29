package com.example.moodtracker;

import java.util.ArrayList;

/**
 * Its an object that contains all the information and functions needed by a user
 */
public class User {

    private String userID, email, password, profilePic;
    private int numFollwers=0;


    private ArrayList<Mood> moodHistory = new ArrayList<>();
    private ArrayList<Following> followingList = new ArrayList<>();
    private ArrayList<Notification> notification = new ArrayList<>();

    public User(String userID, String email, String password, ArrayList<Mood> moodHistory, String profilePic) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.moodHistory = moodHistory;
        this.profilePic = profilePic;
    }

    public User(String userID, String email, String password, ArrayList<Mood> moodHistory) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.moodHistory = moodHistory;
    }

    public User(String userID, String email, String password, String profilePic) {
        this.userID = userID;
        this.profilePic = profilePic;
        this.email = email;
        this.password = password;
    }
    public User(String userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
    }
    public User() {
        //do nothing
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) { this.userID = userID; }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public ArrayList<Mood> getMoodHistory() { return moodHistory; }
    public void setMoodHistory(ArrayList<Mood> moods) { moodHistory = moods; }
    public ArrayList<Following> getFollowingList() { return followingList; }
    public void setFollowingList(ArrayList<Following> friends) { followingList = friends; }
    public ArrayList<Notification> getNotification() { return notification; }
    public void setNotification(ArrayList<Notification> notification) { this.notification = notification; }

    public int getNumFollwers() {
        return numFollwers;
    }
    public void setNumFollwers(int numFollwers) {
        this.numFollwers = numFollwers;
    }
}