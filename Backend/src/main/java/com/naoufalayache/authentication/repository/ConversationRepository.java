package com.naoufalayache.authentication.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.naoufalayache.authentication.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    public Optional<Conversation> findById(Long id);

    @Query(
        value = "SELECT * FROM conversation INNER JOIN " +
                "conversation_member ON conversation_member.conversation_id = conversation.id " +
                "WHERE conversation_member.user_id = :userId",
        nativeQuery = true
    )
    Page<Conversation> getConversations(Long userId, Pageable pageable);
}
