package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.service.Prac02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // 게시판 상세보기
        return service.getBoard(bbsseq);
    }

    @PutMapping("/{bbsseq}")
    public String reWriteBoard(@RequestBody Board board, @PathVariable int bbsseq){
        log.debug("reWriteBoard Board : {}.", board);
        // 게시판 수정
        boolean isSuccess = service.reWriteBoard(board, bbsseq);

        if(isSuccess){
            return "OK";
        }
        return "null";
    }

    @DeleteMapping("/{bbsseq}")
    public String deleteBoard(@PathVariable int bbsseq){
        log.debug("deleteBoard : {}.", bbsseq);
        // 게시판 삭제
        boolean isSuccess = service.deleteBoard(bbsseq);
        if(isSuccess){
            return "OK";
        }
        return "null";
    }

    @GetMapping("/bbslist")
    public List<Board> boardList(@RequestBody SearchParam search){
        log.debug("boardList : {}.", search);
        // 게시판 목록 조회
        // default : search = null, pageNum = 1, exposedCount = 5개 씩..
        List<Board> boardlist = service.boardList(search);
        // boardlist가 없는경우 예외처리 추가예정.. (jdbctemplate)




        return boardlist;
    }



}
