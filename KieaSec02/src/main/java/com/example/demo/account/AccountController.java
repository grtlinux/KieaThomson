package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/create")
	public Account create() {
		Account account = new Account();
		account.setEmail("grtlinux@gmail.com");
		account.setPassword("kang123");
		
		return this.accountService.save(account);
	}
}
