package com.whatsappyoutube.apis;

import com.whatsappyoutube.dto.request.SendMessageRequest;
import com.whatsappyoutube.dto.response.ApiResponse;
import com.whatsappyoutube.entities.Message;
import com.whatsappyoutube.entities.User;
import com.whatsappyoutube.exceptions.ChatException;
import com.whatsappyoutube.exceptions.MessageException;
import com.whatsappyoutube.exceptions.UserException;
import com.whatsappyoutube.service.MessageService;
import com.whatsappyoutube.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageApi {

    private final MessageService messageService;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest request, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        request.setUserId(user.getId());
        Message message = messageService.sendMessage(request);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatMessages(chatId, user);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);
        ApiResponse response = new ApiResponse("Message deleted successfully", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
