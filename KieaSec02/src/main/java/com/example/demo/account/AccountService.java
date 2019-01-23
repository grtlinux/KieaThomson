package com.example.demo.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Account save(Account account) {
		if (!account.getPassword().contains("{noop}")) {
			account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		}
		return this.accountRepository.save(account);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByEmail(username);
		
		List<GrantedAuthority> lstAuthority = new ArrayList<>();
		lstAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		System.out.println(">>>>> " + account);
		
		return new User(account.getEmail(), account.getPassword(), lstAuthority);
	}
}
