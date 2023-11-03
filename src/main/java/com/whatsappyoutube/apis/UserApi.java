package com.whatsappyoutube.apis;

import com.whatsappyoutube.dto.request.UpdateUserRequest;
import com.whatsappyoutube.dto.response.ApiResponse;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.UserException;
import com.whatsappyoutube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest userRequest, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(), userRequest);
        ApiResponse response = new ApiResponse("User updated successfully", true);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
