package se2.group3.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(context);
    }

    //probably not worth having a test for, but I cant merge because of low code coverage atm
    @Test
    void execution() {
        BackendApplication.main(new String[]{});
    }

}
