package com.naoufalayache.authentication.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.naoufalayache.DTO.ConversationDTO;
import com.naoufalayache.DTO.CreateConvDTO;
import com.naoufalayache.DTO.ResponseDTO;
import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.model.ConversationMember;
import com.naoufalayache.authentication.repository.ConversationMemberRepository;
import com.naoufalayache.authentication.repository.ConversationRepository;
import com.naoufalayache.authentication.repository.UserRepository;
import com.naoufalayache.authentication.services.ConversationService;
import com.naoufalayache.exception.AppError;

@Service 
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final UserRepository userRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository, ConversationMemberRepository conversationMemberRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationMemberRepository = conversationMemberRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseDTO delete(Conversation conversation) {
        conversationRepository.delete(conversation);
        return new ResponseDTO(HttpStatus.CREATED, "Conversation supprimé avec succès");
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return this.conversationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationDTO> getConversations(Long userId, int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new AppError("La page doit être positive ou nulle et la taille entre 1 et 100");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Order.desc("createdAt"),
                        Sort.Order.desc("id")));

        return conversationRepository.getConversations(userId, pageable)
                .getContent()
                .stream()
                .map(conversation -> {
                    ConversationDTO dto = new ConversationDTO();
                    Long nombrePersonnes = conversationMemberRepository.countByConversationId(conversation.getId());
                    dto.setNmbrPeople(nombrePersonnes);
                    dto.setNom(conversation.getName());
                    dto.setCreatedAt(conversation.getCreatedAt());
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional 
    public ResponseDTO create(CreateConvDTO createConvDTO) {
        Conversation conversation = new Conversation();
        conversation.setName(createConvDTO.getName());
        conversation.setType(createConvDTO.getType());
        conversationRepository.save(conversation);

        for(Long userId : createConvDTO.getUsersId()){
            ConversationMember conversationMember = new ConversationMember();
            conversationMember.setConversation(conversation);
            conversationMember.setUser(userRepository.findById(userId).orElseThrow(() -> new AppError("Utilisateur introuvable : " + userId)));
            conversationMemberRepository.save(conversationMember);
        }

        return new ResponseDTO(HttpStatus.CREATED, "Conversation créée avec succès");
    }
}
