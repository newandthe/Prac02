package com.example.demo.model;

import lombok.Data;

@Data
public class SearchParam {
    private String search;
    private int pageNum;
    private int exposedCount;
}
