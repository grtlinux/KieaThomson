package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	private final boolean flag = true;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/create")
	public Account create() {
		
		if (flag) {
			// repository
			Account account = new Account();
			account.setEmail("grtlinux@gmail.com");
			account.setPassword("{noop}kang123");
			
			return this.accountRepository.save(account);
		} else {
			// service
			Account account = new Account();
			account.setEmail("grtlinux@gmail.com");
			account.setPassword("kang123");
			
			return this.accountService.save(account);
		}
	}
}
