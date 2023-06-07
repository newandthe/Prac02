package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SearchParam {
    private String search;

//    @Size(min = 1, message = "페이지 검색은 양수여야 합니다.")
    private int pageNum;

//    @Size(min = 1, message = "페이지당 노출 개수는 양수여야 합니다.")
    private int exposedCount;
}
