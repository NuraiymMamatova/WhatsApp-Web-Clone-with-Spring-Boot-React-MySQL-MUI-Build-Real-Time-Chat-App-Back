package com.whatsappyoutube.service.serviceimpl;

import com.whatsappyoutube.dto.request.SendMessageRequest;
import com.whatsappyoutube.entities.Chat;
import com.whatsappyoutube.entities.Message;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.ChatException;
import com.whatsappyoutube.exceptions.MessageException;
import com.whatsappyoutube.exceptions.UserException;
import com.whatsappyoutube.repositories.MessageRepository;
import com.whatsappyoutube.service.ChatService;
import com.whatsappyoutube.service.MessageService;
import com.whatsappyoutube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException {
        User user = userService.findUserById(request.getUserId());
        Chat chat = chatService.findChatById(request.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You are not releted to this chat " + chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new MessageException("Message not found with id message " + messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);
        if (message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't delete another user's message " + reqUser.getFullName());
    }
}
