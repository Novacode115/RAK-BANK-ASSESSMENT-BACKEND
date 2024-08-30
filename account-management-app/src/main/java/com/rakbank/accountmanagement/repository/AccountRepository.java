package com.rakbank.accountmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rakbank.accountmanagement.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	boolean existsByEmail(String email);

	Optional<Account> findByEmail(String email);

}
