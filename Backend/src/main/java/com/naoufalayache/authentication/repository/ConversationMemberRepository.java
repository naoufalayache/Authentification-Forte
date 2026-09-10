package com.naoufalayache.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naoufalayache.authentication.model.Conversation;
import com.naoufalayache.authentication.model.ConversationMember;
import com.naoufalayache.authentication.model.User;

public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {
    public Optional<ConversationMember> findById(Long id);
    public Optional<ConversationMember> findByUserAndConversation(User user,Conversation conversation);
    long countByConversationId(Long conversationId);
}
