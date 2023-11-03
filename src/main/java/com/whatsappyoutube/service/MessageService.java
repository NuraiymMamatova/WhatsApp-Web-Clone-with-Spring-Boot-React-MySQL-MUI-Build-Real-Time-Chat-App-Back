package com.whatsappyoutube.service;

import com.whatsappyoutube.dto.request.SendMessageRequest;
import com.whatsappyoutube.entities.Message;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.ChatException;
import com.whatsappyoutube.exceptions.MessageException;
import com.whatsappyoutube.exceptions.UserException;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequest request) throws UserException, ChatException;

    List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    Message findMessageById(Integer messageId) throws MessageException;

    void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}
