package com.rakbank.accountmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rakbank.accountmanagement.dto.AccountDTO;
import com.rakbank.accountmanagement.dto.PasswordUpdateRequest;
import com.rakbank.accountmanagement.service.AccountService;
import com.rakbank.accountmanagement.service.SessionService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    private String getSessionIdFromRequest(HttpHeaders headers) {
        return headers.getFirst("Session-ID"); 
    }

    private boolean isSessionValid(String sessionId) {
        return sessionService.isValidSession(sessionId);
    }
    
    
    @PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerAccount(@Valid @RequestBody AccountDTO accountDTO) {
		Map<String, Object> response = accountService.createAccount(accountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public Map<String, Object> loginAccount(@RequestBody AccountDTO accountDTO) {
		return accountService.loginAccount(accountDTO);
	}

    @PatchMapping("/{id}/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable Long id,
                                                               @RequestBody PasswordUpdateRequest request,
                                                               @RequestHeader HttpHeaders headers) {
        String sessionId = getSessionIdFromRequest(headers);
        if (sessionId == null || !isSessionValid(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "failure", "message", "Invalid or missing session ID"));
        }
        Map<String, Object> response = accountService.updatePassword(id, request.getNewPassword());
        String status = (String) response.get("status");
        return "success".equals(status) ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAccount(@PathVariable Long id,
                                                               @RequestBody AccountDTO accountDTO,
                                                               @RequestHeader HttpHeaders headers) {
        String sessionId = getSessionIdFromRequest(headers);
        if (sessionId == null || !isSessionValid(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "failure", "message", "Invalid or missing session ID"));
        }
        Map<String, Object> response = accountService.updateAccount(id, accountDTO);
        String status = (String) response.get("status");
        return "success".equals(status) ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@RequestHeader HttpHeaders headers) {
        String sessionId = getSessionIdFromRequest(headers);
        if (sessionId == null || !isSessionValid(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<AccountDTO> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id,
                                                     @RequestHeader HttpHeaders headers) {
        String sessionId = getSessionIdFromRequest(headers);
        if (sessionId == null || !isSessionValid(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return accountService.getAccountById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable Long id,
                                                              @RequestHeader HttpHeaders headers) {
        String sessionId = getSessionIdFromRequest(headers);
        if (sessionId == null || !isSessionValid(sessionId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "failure", "message", "Invalid or missing session ID"));
        }
        Map<String, Object> response = accountService.deleteAccount(id);
        String status = (String) response.get("status");
        return "success".equals(status) ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
