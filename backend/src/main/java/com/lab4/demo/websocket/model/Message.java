package com.lab4.demo.websocket.model;


import lombok.*;
import org.springframework.stereotype.Controller;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Message {

    private String title;
    private String text;
}
