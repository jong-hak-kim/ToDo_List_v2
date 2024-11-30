package com.todo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "todo")
public class AppConfig {

    public Hello hello;

    @Data
    public static class Hello {
        public String name;
        public String home;
        public String hobby;
        public Long age;
    }
}
