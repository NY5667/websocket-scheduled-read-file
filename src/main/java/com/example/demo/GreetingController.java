package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

@Controller
@EnableScheduling
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(HelloMessage message) throws InterruptedException {
        Thread.sleep(2000);
        return new Greeting("Hello, " +
                HtmlUtils.htmlEscape(message.getName()));
    }

    private static final String PATHNAME = "C:\\Users\\ny5667\\Desktop\\0821\\1.txt";


    @ResponseBody
    @GetMapping("/readLog")
    public String readLog() throws IOException {
        // File path is passed as parameter
        File file = new File(
                PATHNAME);

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st = "";
        StringBuilder builder = new StringBuilder();
        // Condition holds true till
        // there is character in a string

        while ((st = br.readLine()) != null){
            // Print the string
            System.out.println(st);
            builder.append(st);
        }
        return builder.toString();
    }

}
