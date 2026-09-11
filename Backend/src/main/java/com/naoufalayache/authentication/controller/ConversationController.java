package com.naoufalayache.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.naoufalayache.DTO.ConversationDTO;
import com.naoufalayache.DTO.CreateConvDTO;
import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.services.ConversationService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController 
@RequestMapping("api/v1/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService){
        this.conversationService = conversationService;
    }

    @GetMapping
    public List<ConversationDTO> getConversations(
            @RequestParam("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return conversationService.getConversations(userId, page, size);
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody CreateConvDTO createConvDTO){
        if (createConvDTO.getUsersId().size() > 2){
            createConvDTO.setType(true);
        }
        return this.conversationService.create(createConvDTO);
    }
    
}
