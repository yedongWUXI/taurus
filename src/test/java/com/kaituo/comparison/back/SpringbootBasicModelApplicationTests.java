package com.kaituo.comparison.back;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
@RestController
@SpringBootTest
public class SpringbootBasicModelApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicModelApplicationTests.class, args);
    }
}
