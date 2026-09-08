package com.naoufalayache.authentication.services.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.repository.ConversationRepository;
import com.naoufalayache.authentication.services.ConversationService;
import org.springframework.transaction.annotation.Transactional;

public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    @Override
    @Transactional 
    public ResponseDTO delete(Conversation conversation){
        conversationRepository.delete(conversation);
        return new ResponseDTO(HttpStatus.CREATED,"Conversation supprimé avec succès");
    }

    public Optional<Conversation> findById(Long id){
        return this.conversationRepository.findById(id);
    }
}
