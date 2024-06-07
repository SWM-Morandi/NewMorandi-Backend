package kr.co.morandi.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
    @Bean(name = "baekjoonJudgementExecutor")
    public ThreadPoolTaskExecutor baekjoonJudgementThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 수
        executor.setMaxPoolSize(10);  // 최대 스레드 수
        executor.setQueueCapacity(25);  // 대기열 크기e
        executor.setThreadNamePrefix("baekjoon-judgement-");
        executor.initialize();
        return executor;
    }
}