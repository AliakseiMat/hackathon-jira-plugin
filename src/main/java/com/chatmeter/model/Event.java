package com.chatmeter.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    private String source;
    private String type;
    private Integer rating;
    private String text;
    private String location;
}
