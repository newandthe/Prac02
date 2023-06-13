package com.example.demo.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


/*
    DATA Annotation 내에는 아래가 포함
    Getter Setter
    AllArgsConstructor
    NoArgsConstructor
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int bbsseq;

    @NotBlank(message = "제목은 공백일 수 없습니다")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다")
    private String content;

    @NotBlank(message = "작성자는 공백일 수 없습니다") @Size(max = 10, message = "작성자 이름은 10글자 이내로 작성해주세요.")
    private String author;

    private int del;
}
