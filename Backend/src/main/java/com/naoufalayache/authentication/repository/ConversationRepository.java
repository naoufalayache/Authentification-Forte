package com.naoufalayache.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naoufalayache.authentication.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    public Optional<Conversation> findById(Long id);
}
