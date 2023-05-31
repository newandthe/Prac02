package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.service.Prac02Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class Prac02Controller {

    private final Prac02Service service;

    @Autowired
    public Prac02Controller(Prac02Service service){
        this.service = service;
    }

    @PostMapping
    public Board createBoard(@RequestBody Board board){
        // 게시판 등록
        return service.createBoard(board);
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable int bbsseq){
        // 게시판 상세보기
        return service.getBoard(bbsseq);
    }

}
