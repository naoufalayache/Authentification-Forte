package com.naoufalayache.authentication.services;

import java.util.Optional;

import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.model.ConversationMember;
import com.naoufalayache.authentication.model.User;

public interface ConversationMemberService {
    public Optional<ConversationMember> findById(Long id);
    public Optional<ConversationMember> findByUserAndConversation(User user,Conversation conversation);
}
