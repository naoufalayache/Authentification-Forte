package com.naoufalayache.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateConvDTO {
    @NotBlank
    List<Long> usersId;
    @NotBlank 
    String name;
    Boolean type;
}
