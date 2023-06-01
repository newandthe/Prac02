package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.service.Prac02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController         // Rest API ( responsebody 생략 가능 )
@RequestMapping("/boards")
@Slf4j
public class Prac02Controller {

    private final Prac02Service service;

    @Autowired
    public Prac02Controller(Prac02Service service) {
        this.service = service;
    }

    /* C = PostMapping , R = GetMapping, U = PutMapping, D = DeleteMapping */

    @PostMapping("")
    public String createBoard(@RequestBody Board board) {
        log.debug("createBoard Board : {}.", board);

        // 게시판 등록

//        System.out.println(board.toString());

        boolean isSuccess = service.createBoard(board);

        // ExceptionHandling으로 추후에 바꾸자 ..
        if(isSuccess){
            return "OK";
        }
        return "check";
    }

    @GetMapping("/{bbsseq}")
    public Board getBoard(@PathVariable int bbsseq) {
        log.debug("getBoard bbsseq : {}.", bbsseq);
        System.out.println("getBoard : " + bbsseq);
        // 게시판 상세보기
        return service.getBoard(bbsseq);
    }

    @PutMapping("/{bbsseq}")
    public String reWriteBoard(@RequestBody Board board, @PathVariable int bbsseq){
        log.debug("reWriteBoard Board : {}.", board);
        System.out.println("reWriteBoard : " + board.toString() + bbsseq);

        boolean isSuccess = service.reWriteBoard(board, bbsseq);

        if(isSuccess){
            return "OK";
        }
        return "null";
    }

    @DeleteMapping("/{bbsseq}")
    public String deleteBoard(@PathVariable int bbsseq){
        log.debug("deleteBoard : {}.", bbsseq);
        boolean isSuccess = service.deleteBoard(bbsseq);
        if(isSuccess){
            return "OK";
        }
        return "null";
    }



}
