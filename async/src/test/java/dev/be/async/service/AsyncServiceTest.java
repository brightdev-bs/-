package dev.be.async.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @DisplayName("asynchService의 sendMail 테스트")
    @Test
    void sendMail() {
        System.out.println("[test] :: " + Thread.currentThread().getName());
        asyncService.sendMail();
    }


}