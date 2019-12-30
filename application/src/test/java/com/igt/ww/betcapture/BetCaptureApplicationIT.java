package com.igt.ww.betcapture;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import com.igt.ww.postgresql.test.*;

@PostgreSQLTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BetCaptureApplicationIT {

	@Test
	void contextLoads() {}
}
