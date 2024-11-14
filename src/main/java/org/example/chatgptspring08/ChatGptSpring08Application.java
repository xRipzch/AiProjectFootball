package org.example.chatgptspring08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChatGptSpring08Application {

    public static void main(String[] args) {

        SpringApplication.run(ChatGptSpring08Application.class, args);
    }

}
