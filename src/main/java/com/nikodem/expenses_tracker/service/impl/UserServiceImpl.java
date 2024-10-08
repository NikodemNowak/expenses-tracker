package com.nikodem.expenses_tracker.service.impl;

import com.nikodem.expenses_tracker.dto.PatchUserDTO;
import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.exception.EmailTakenException;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.UserMapper;
import com.nikodem.expenses_tracker.model.User;
import com.nikodem.expenses_tracker.repository.UserRepository;
import com.nikodem.expenses_tracker.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
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
        User user = userRepository
                .findByIdAndExpiredIsFalse(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(
                        "User not found"
                ));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDTO addUser(PostUserDTO postUserDTO) {
        if (userRepository.findByEmailAndExpiredIsFalse(postUserDTO.getEmail()).isPresent()) {
            throw new EmailTakenException("User with email " + postUserDTO.getEmail() + " already exists");
        }
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(postUserDTO)
                )
        );
    }

    @Override
    @Transactional
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
