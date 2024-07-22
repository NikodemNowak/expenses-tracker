package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

interface IUserMapper {
    UserDTO toDto(User user);
    User toEntity(PostUserDTO postUserDTO);

    List<UserDTO> toDto(List<User> users);
}

@Component
public class UserMapper implements IUserMapper{

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
    public User toEntity(PostUserDTO postUserDTO) {
        if (postUserDTO == null) return null;
        // TODO: add exception instead return null
        return new User(
                postUserDTO.getEmail().trim(),
                postUserDTO.getName().trim()
        );
    }

    @Override
    public List<UserDTO> toDto(List<User> users) {
        if (users == null) return null;
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(toDto(user));
        }
        return userDTOs;
    }
}
