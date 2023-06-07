package com.example.demo.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;


/*
    DATA Annotation 내에는 아래가 포함
    Getter Setter
    AllArgsConstructor
    NoArgsConstructor
*/

@Data
public class Board {
    private int bbsseq;

    @NotBlank(message = "제목은 공백일 수 없습니다")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다")
    private String content;

    private int del;
}
