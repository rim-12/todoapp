package com.example.demo.Controller;

import com.example.demo.Entity.DTO.UserRequestDto;
import com.example.demo.Entity.DTO.UserResponseDto;
import com.example.demo.Service.UserService;
import com.example.demo.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users", produces = "application/json", consumes = "application/json")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userDto) {
        try { // Validation with Function interface
            // Call the createUser method in the service
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle validation error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById( @PathVariable Long id) throws UserNotFoundException {

        try {
            return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User Not Found!",HttpStatus.NOT_FOUND);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userDto) {
        try {
            UserResponseDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return null;
        }
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) throws UserNotFoundException {
        try {
            userService.deleteUser(id);
            return "User id: "+id+" deleted!";
        } catch (Exception e) {
            return "\nUser Not Found!";
        }

    }
    }
