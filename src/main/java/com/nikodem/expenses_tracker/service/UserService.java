package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.PatchUserDTO;
import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.exception.EmptyArgumentException;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.UserMapper;
import com.nikodem.expenses_tracker.model.User;
import com.nikodem.expenses_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

interface IUserService {
    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO addUser(PostUserDTO postUserDTO);
    UserDTO updateUser(PatchUserDTO patchUserDTO);
    void setUserExpired(UUID id);
}

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        return userMapper.toDto(
                userRepository
                        .findAllByExpiredIsFalse()
                        .orElseThrow(() -> new NotFoundException(
                                "Users not found"
                        ))
        );
    }

    @Override
    public UserDTO findById(String id) {
        if (id.isEmpty()) throw new EmptyArgumentException("Given id is empty");
        User user = userRepository
                .findByIdAndExpiredIsFalse(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(
                        "User not found"
                ));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO addUser(PostUserDTO postUserDTO) {
        if (userRepository.findByEmailAndExpiredIsFalse(postUserDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + postUserDTO.getEmail() + " already exists");
            //TODO: make custom exception
        }
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(postUserDTO)
                )
        );
    }

    @Override
    public UserDTO updateUser(PatchUserDTO patchUserDTO) {
        User user = userRepository
                .findByIdAndExpiredIsFalse(patchUserDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        "User not found"
                ));
        if (patchUserDTO.getEmail() != null) user.setEmail(patchUserDTO.getEmail());
        if (patchUserDTO.getName() != null) user.setName(patchUserDTO.getName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void setUserExpired(UUID id) {
        User user = userRepository
                .findByIdAndExpiredIsFalse(id)
                .orElseThrow(() -> new NotFoundException(
                        "User not found"
                ));
        user.setExpired(true);
        userRepository.save(user);
    }
}
