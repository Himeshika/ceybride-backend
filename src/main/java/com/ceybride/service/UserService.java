package com.ceybride.service;

import com.ceybride.dto.auth.ChangePasswordDTO;
import com.ceybride.dto.auth.UserResponseDTO;
import com.ceybride.entity.User;

public interface UserService {
    User getCurrentUserEntity();
    UserResponseDTO getCurrentUser();
    void changePassword(ChangePasswordDTO request);
}
