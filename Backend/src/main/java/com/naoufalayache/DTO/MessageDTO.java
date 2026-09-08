package com.naoufalayache.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    Long id;
    Long idConversation;
    Long idUser;

    String value;
    LocalDateTime createdAt;
}
