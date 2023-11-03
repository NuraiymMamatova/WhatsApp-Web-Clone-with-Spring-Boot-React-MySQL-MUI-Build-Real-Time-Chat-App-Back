package com.whatsappyoutube.service.serviceimpl;

import com.whatsappyoutube.dto.request.GroupChatRequest;
import com.whatsappyoutube.entities.Chat;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.ChatException;
import com.whatsappyoutube.exceptions.UserException;
import com.whatsappyoutube.repositories.ChatRepository;
import com.whatsappyoutube.service.ChatService;
import com.whatsappyoutube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
        if (isChatExist != null) {
            return isChatExist;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);
        return null;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
        return chat.get();
        }
       throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return chatRepository.findAllChatByUserId(user.getId());
    }

    @Override
    public Chat createGroup(GroupChatRequest groupChatRequest, User reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatImage(groupChatRequest.getChatImage());
        group.setChatName(groupChatRequest.getChatName());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);
        for (Integer userId : groupChatRequest.getUserIds()) {
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }
        return group;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user =  userService.findUserById(userId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not admin");
            }
        }
       throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getUsers().contains(reqUser)) {
                chat.setChatName(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user =  userService.findUserById(userId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            }
            else if (chat.getUsers().contains(reqUser)) {
                if (user.getId().equals(reqUser.getId())) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            }
                throw new UserException("You can't remove another user");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}
