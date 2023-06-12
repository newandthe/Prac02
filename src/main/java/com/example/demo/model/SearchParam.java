package com.example.demo.model;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchParam {
    @Builder.Default private String search="";

//    @Size(min = 1, message = "페이지 검색은 양수여야 합니다.")
    @Builder.Default private int pageNum = 1;

//    @Size(min = 1, message = "페이지당 노출 개수는 양수여야 합니다.")
    @Builder.Default private int exposedCount = 5;
}
