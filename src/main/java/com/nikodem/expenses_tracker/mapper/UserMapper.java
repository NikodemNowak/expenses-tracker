package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.model.User;

import java.util.List;

public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(PostUserDTO postUserDTO);
    List<UserDTO> toDto(List<User> users);
}


