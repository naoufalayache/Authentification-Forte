package com.naoufalayache.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendedDTO {
    @NotBlank 
    String value;
    @NotBlank
    Long idUser;
    @NotBlank
    Long idConversation;
    Long idMessage;
    LocalDateTime createdAt;
}
