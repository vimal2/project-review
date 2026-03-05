package com.revplay.dto.response;

public class UserProfileResponse {

    private String username;
    private String email;
    private String displayName;
    private String bio;
    private String profileImage;

    public UserProfileResponse(String username, String email,
                               String displayName, String bio,
                               String profileImage) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public String getProfileImage() { return profileImage; }
}