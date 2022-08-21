package com.example.demo;


import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledPushMessages {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final Faker faker;

//    private static final String PATHNAME = "C:\\Users\\ny5667\\Desktop\\0821\\1.txt";

    @Value("${pathname:}")
    private String PATHNAME;

    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        faker = new Faker();
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() throws IOException {

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
//            System.out.println(st);
            builder.append(st + "\r\n");
        }
        simpMessagingTemplate.convertAndSend("/topic/greetings",
                new Greeting(builder.toString()));
    }

}