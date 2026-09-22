package com.naoufalayache.authentication.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.model.ConversationMember;
import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.repository.ConversationMemberRepository;
import com.naoufalayache.authentication.services.ConversationMemberService;

@Service 
public class ConversationMemberServiceImpl implements ConversationMemberService {
    
    private final ConversationMemberRepository conversationMemberRepository;

    public ConversationMemberServiceImpl(ConversationMemberRepository conversationMemberRepository){
        this.conversationMemberRepository = conversationMemberRepository;
    }
    
    public Optional<ConversationMember> findById(Long id){
        return this.conversationMemberRepository.findById(id);
    }

    @Override
    public Optional<ConversationMember> findByUserAndConversation(User user, Conversation conversation) {
        return this.conversationMemberRepository.findByUserAndConversation(user, conversation);
    }
}
