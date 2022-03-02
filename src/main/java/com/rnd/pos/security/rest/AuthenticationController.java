package com.rnd.pos.security.rest;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    
	@GetMapping("/login")
    public ResponseEntity<String> test(HttpSession session) {
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		return ResponseEntity.ok(sessionId);
    }
}