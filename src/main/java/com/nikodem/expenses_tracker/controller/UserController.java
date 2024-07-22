package com.nikodem.expenses_tracker.controller;

import com.nikodem.expenses_tracker.dto.PatchUserDTO;
import com.nikodem.expenses_tracker.dto.PostUserDTO;
import com.nikodem.expenses_tracker.dto.UserDTO;
import com.nikodem.expenses_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.findById(userId.toString()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody PostUserDTO postUserDTO) {
        return new ResponseEntity<>(userService.addUser(postUserDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody PatchUserDTO patchUserDTO) {
        return ResponseEntity.ok(userService.updateUser(patchUserDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.setUserExpired(UUID.fromString(userId));
        return ResponseEntity.ok(null);
    }
}
