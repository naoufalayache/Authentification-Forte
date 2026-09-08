package com.naoufalayache.authentication.services;

import java.util.List;
import java.util.Optional;

import com.naoufalayache.DTO.MessageDTO;
import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Message;

public interface MessageService {
    public ResponseDTO upsert(Message message);
    public Optional<Message> findById(Long id);
    public ResponseDTO delete(Message message);
    List<MessageDTO> getMessages(Long conversationId, int page, int size);
}
