package com.whatsappyoutube.service.serviceimpl;

import com.whatsappyoutube.config.TokenProvider;
import com.whatsappyoutube.dto.request.UpdateUserRequest;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.UserException;
import com.whatsappyoutube.repositories.UserRepository;
import com.whatsappyoutube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);
        if (email != null) {
            throw new BadCredentialsException("received invalid token");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("user not found with email " + email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest request) throws UserException {
        User user = findUserById(userId);
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

}
