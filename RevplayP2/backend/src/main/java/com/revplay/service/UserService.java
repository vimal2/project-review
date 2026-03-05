package com.revplay.service;

import com.revplay.dto.request.UserProfileUpdateRequest;
import com.revplay.dto.response.UserProfileResponse;
import com.revplay.dto.response.UserStatsResponse;

public interface UserService {

    UserProfileResponse getProfile(String username);

    UserProfileResponse updateProfile(String username, UserProfileUpdateRequest updatedUser);

    UserStatsResponse getStats(String username);
}
