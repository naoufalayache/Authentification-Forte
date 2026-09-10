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
public class ConversationDTO {
    @NotBlank 
    Long nmbrPeople;
    @NotBlank
    String nom;
    @NotBlank
    LocalDateTime createdAt;
}
