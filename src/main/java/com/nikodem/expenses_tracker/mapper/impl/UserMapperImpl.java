package com.nikodem.expenses_tracker.mapper.impl;

import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.mapper.UserMapper;
import com.nikodem.expenses_tracker.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    @Override
    public List<UserDTO> toDto(List<User> users) {
        if (users == null || users.isEmpty()) return null;
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(toDto(user));
        }
        return userDTOs;
    }

    @Override
    public User toEntity(PostUserDTO postUserDTO) {
        return new User(
                postUserDTO.getEmail().trim(),
                postUserDTO.getName().trim()
        );
    }
}
