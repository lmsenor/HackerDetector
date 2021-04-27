package com.HackerDetector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class HackerDetectorApplicationTests {

	@Autowired
	MockMvc mock;
	
	@Test
	void testHackerDetector() throws Exception {
		mock.perform(get("/Will.Smith")).andDo(print());
	}

}
