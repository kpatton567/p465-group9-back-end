package com.group9.prevue;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TestController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}
