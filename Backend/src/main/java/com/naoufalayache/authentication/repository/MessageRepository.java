package com.naoufalayache.authentication.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.naoufalayache.authentication.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    public Optional<Message> findById(Long id);
    Page<Message> findByConversationId(Long conversationId, Pageable pageable);
}
