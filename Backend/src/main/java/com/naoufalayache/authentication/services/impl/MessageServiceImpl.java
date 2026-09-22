package com.naoufalayache.authentication.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.naoufalayache.DTO.MessageDTO;
import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Message;
import com.naoufalayache.authentication.repository.MessageRepository;
import com.naoufalayache.authentication.services.MessageService;
import com.naoufalayache.exception.AppError;

@Service 
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    @Transactional
    public ResponseDTO upsert(Message message) {
        boolean creation = message.getId() == null;
        String response = creation ? "Message créé avec succès" : "Message modifié avec succès";
        messageRepository.save(message);
        return new ResponseDTO(HttpStatus.CREATED, response);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> getMessages(Long conversationId, int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new AppError("La page doit être positive ou nulle et la taille entre 1 et 100");
        }

        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(
                Sort.Order.desc("createdAt"),
                Sort.Order.desc("id")
            )
        );

        return messageRepository.findByConversationId(conversationId, pageable)
            .getContent()
            .stream()
            .map(message -> {
                MessageDTO dto = new MessageDTO();
                dto.setCreatedAt(message.getCreatedAt());
                dto.setId(message.getId());
                dto.setIdConversation(conversationId);
                dto.setIdUser(message.getUser().getId());
                return dto;
            })
            .toList();
    }

    @Override
    @Transactional
    public ResponseDTO delete(Message message) {
        messageRepository.delete(message);
        return new ResponseDTO(HttpStatus.CREATED, "Message supprimé avec succès");
    }

}
