package com.whatsappyoutube.service;

import com.whatsappyoutube.dto.request.UpdateUserRequest;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.UserException;

import java.util.List;

public interface UserService {

    User findUserById(Integer id) throws UserException;

    User findUserProfile(String jwt) throws UserException;

    User updateUser(Integer userId, UpdateUserRequest request) throws UserException;

    List<User> searchUser(String query);

}
