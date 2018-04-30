package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class WelcomeController {

    String message = "hello default";

    public WelcomeController(@Value("${WELCOME_MESSAGE}") String input) {
        this.message = input;
    }

    @GetMapping("/")
    public String sayHello() {
        return message;
    }
}