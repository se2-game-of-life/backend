package se2.group3.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebsocketConfigurationTests {

    @Autowired
    private WebsocketConfiguration websocketConfiguration;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(websocketConfiguration);
    }
}
