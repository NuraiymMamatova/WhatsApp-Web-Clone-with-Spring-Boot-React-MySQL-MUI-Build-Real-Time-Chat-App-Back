package com.whatsappyoutube.service;

import com.whatsappyoutube.dto.request.GroupChatRequest;
import com.whatsappyoutube.entities.Chat;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.ChatException;
import com.whatsappyoutube.exceptions.UserException;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, Integer userId2) throws UserException;

    Chat findChatById(Integer chatId) throws ChatException;

    List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    Chat createGroup(GroupChatRequest groupChatRequest, User reqUser) throws UserException;

    Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;

    Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException;

    Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;

    void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}
