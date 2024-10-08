package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.PatchUserDTO;
import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO addUser(PostUserDTO postUserDTO);
    UserDTO updateUser(PatchUserDTO patchUserDTO);
    void setUserExpired(UUID id);
}


