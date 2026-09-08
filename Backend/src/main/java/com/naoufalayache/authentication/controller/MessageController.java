package com.naoufalayache.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.naoufalayache.DTO.DeleteConvDTO;
import com.naoufalayache.DTO.MessageDTO;
import com.naoufalayache.DTO.MessageSendedDTO;
import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.model.ConversationMember;
import com.naoufalayache.authentication.model.Message;
import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.services.ConversationMemberService;
import com.naoufalayache.authentication.services.ConversationService;
import com.naoufalayache.authentication.services.MessageService;
import com.naoufalayache.authentication.services.UserService;
import com.naoufalayache.exception.AppError;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {

    private final UserService userService;
    private final ConversationService conversationService;
    private final ConversationMemberService conversationMemberService;
    private final MessageService messageService;

    public MessageController(UserService userService, ConversationService conversationService,
            ConversationMemberService conversationMemberService, MessageService messageService) {
        this.userService = userService;
        this.conversationService = conversationService;
        this.conversationMemberService = conversationMemberService;
        this.messageService = messageService;
    }

    @PostMapping("/upsert")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO upsertMessages(
            @RequestBody MessageSendedDTO messageSendedDTO) {
        Long userId = messageSendedDTO.getIdUser();
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            throw new AppError("L'utilisateur n'existe pas où est indisponible");
        }
        User user = userOptional.get();

        Long convId = messageSendedDTO.getIdConversation();
        Optional<Conversation> convOptional = conversationService.findById(convId);

        if (convOptional.isEmpty()) {
            throw new AppError("La conversation n'existe pas où est indisponible");
        }
        Conversation conversation = convOptional.get();

        Optional<ConversationMember> conversationMemberOptional = conversationMemberService
                .findByUserAndConversation(user, conversation);

        if (conversationMemberOptional.isEmpty()) {
            throw new AppError("La conversation n'existe pas pour l'utilisateur où est indisponible");
        }

        Message message = new Message();
        message.setConversation(conversation);
        message.setUser(user);
        message.setValue(messageSendedDTO.getValue());
        if (messageSendedDTO.getIdMessage() != null) {
            Optional<Message> messageOptional = messageService.findById(messageSendedDTO.getIdMessage());
            if (messageOptional.isEmpty() || !messageOptional.get().getId().equals(message.getId())) {
                throw new AppError("Le message n'existe pas où est indisponible");
            }
            message.setId(messageSendedDTO.getIdMessage());
        }
        return messageService.upsert(message);
    }

    @GetMapping
    public List<MessageDTO> getMessages(
            @RequestParam("conversation") Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return messageService.getMessages(conversationId, page, size);
    }

    @DeleteMapping
    public ResponseDTO deleteMessage(
            @RequestBody DeleteConvDTO deleteConvDTO) {
        
        Long userId = deleteConvDTO.getIdUser();
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            throw new AppError("L'utilisateur n'existe pas où est indisponible");
        }
        User user = userOptional.get();

        Long convId = deleteConvDTO.getIdConversation();
        Optional<Conversation> convOptional = conversationService.findById(convId);

        if (convOptional.isEmpty()) {
            throw new AppError("La conversation n'existe pas où est indisponible");
        }
        Conversation conversation = convOptional.get();

        Optional<ConversationMember> conversationMemberOptional = conversationMemberService
                .findByUserAndConversation(user, conversation);

        if (conversationMemberOptional.isEmpty()) {
            throw new AppError("La conversation n'existe pas pour l'utilisateur où est indisponible");
        }

        Long messageId = deleteConvDTO.getIdMessage();
        Optional<Message> messageOptional = messageService.findById(messageId);

        if (messageOptional.isEmpty()) {
            throw new AppError("Le message n'existe pas où est indisponible");
        }
        Message message = messageOptional.get();

        return messageService.delete(message);
    }
}
