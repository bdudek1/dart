package com.example.dart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:application-test.properties")
@Profile("test")
class DartApplicationTests {

	@Test
	void contextLoads() {
	}

}
