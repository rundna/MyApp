package com.crud.tasks.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Task {
    private Long Id;
    private String title;
    private String content;

}
