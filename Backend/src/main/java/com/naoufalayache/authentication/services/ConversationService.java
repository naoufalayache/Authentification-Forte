package com.naoufalayache.authentication.services;

import java.util.Optional;

import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Conversation;

public interface ConversationService {
    public Optional<Conversation> findById(Long id);
    public ResponseDTO delete(Conversation conversation);
}
