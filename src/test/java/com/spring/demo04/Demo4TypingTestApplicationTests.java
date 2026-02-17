package com.spring.demo04;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // <-- Falta esta importación

@SpringBootTest
@ActiveProfiles("test") // <-- Falta esta etiqueta
class Demo4TypingTestApplicationTests {

	@Test
	void contextLoads() {
	}

}