package dev.be.async.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync // --> 비동기 설정 : 비동기 관련 어노테이션 사용 가능해짐.
public class AsyncConfig {
}
