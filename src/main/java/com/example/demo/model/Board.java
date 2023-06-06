package com.example.demo.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;


/*
    Getter Setter
    AllArgsConstructor
    NoArgsConstructor
*/

@Data
public class Board {
    private int bbsseq;

    @NotNull @NotBlank(message = "제목은 공백일 수 없습니다")
    private String title;

    @NotNull @NotBlank(message = "내용은 공백일 수 없습니다")
    private String content;

    private int del;
}
