package com.rakbank.accountmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {
	
	private Long accountId;

    @NotBlank(message = "Name is mandatory")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password can only contain alphabets and numbers")
    private String password;
}