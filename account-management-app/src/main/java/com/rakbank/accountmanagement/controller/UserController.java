package com.rakbank.accountmanagement.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rakbank.accountmanagement.dto.AccountDTO;
import com.rakbank.accountmanagement.service.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rakbank/user")
public class UserController {
	@Autowired
	private AccountService accountService;

	
}
