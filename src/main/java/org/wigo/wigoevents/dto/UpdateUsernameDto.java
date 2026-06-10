package org.wigo.wigoevents.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsernameDto {
    @NotBlank(message = "Username is required")
    private String username;
}
