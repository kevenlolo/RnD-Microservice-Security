package com.rnd.pos.security.repository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rnd.pos.security.entity.User;

@Service
public class UserRepository {

	public Optional<User> findByUsername(String username) {
		User user = new User("user", "$2a$10$Ehm/1i2BW7Ber8HOxG4KvuQfSAUKM3hj.QRR7rclr.FNgRETWDTxC");
		return Optional.of(user);
	}
}
