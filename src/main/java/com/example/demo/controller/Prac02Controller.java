package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.ExecuteResult;
import com.example.demo.model.SearchParam;
import com.example.demo.service.Prac02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/write") // Create
    public ResponseEntity<Map<String, String>> createBoard(@Valid @RequestBody Board board) {
        log.debug("createBoard Board: {}.", board);

        // 게시판 등록
        boolean isSuccess = service.createBoard(board);

        if (isSuccess) {
            HashMap<String, String> result = new HashMap<>();
            result.put("message", "게시물 등록 성공");
            String timestamp = String.valueOf(LocalDateTime.now());
            result.put("timestamp", timestamp);
            result.put("status", String.valueOf(HttpStatus.CREATED.value()));

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

        HashMap<String, String> result = new HashMap<>();
        result.put("message", "게시물 등록 실패");
        result.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @GetMapping("/{bbsseq}") // Read
    public ResponseEntity<Board> getBoard(@PathVariable int bbsseq) {
        log.debug("getBoard bbsseq: {}.", bbsseq);
        // 게시판 상세보기
        Board board = service.getBoard(bbsseq);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/{bbsseq}") // Update
    public ResponseEntity<Map<String, String>> reWriteBoard(@Valid @RequestBody Board board, @PathVariable int bbsseq) {
        log.debug("reWriteBoard Board: {}.", board);

        // 게시판 수정
        boolean isSuccess = service.reWriteBoard(board, bbsseq);

        if (isSuccess) {
            HashMap<String, String> result = new HashMap<>();
            result.put("message", "게시물 수정 성공");
            String timestamp = String.valueOf(LocalDateTime.now());
            result.put("timestamp", timestamp);
            result.put("status", String.valueOf(HttpStatus.OK.value()));

            return ResponseEntity.ok(result);
        } else {
            HashMap<String, String> result = new HashMap<>();
            result.put("message", "게시물 수정 실패");
            String timestamp = String.valueOf(LocalDateTime.now());
            result.put("timestamp", timestamp);
            result.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }



    // 존재하는지 먼저 파악한후 throw
    @DeleteMapping("/{bbsseq}") // Delete
    public ResponseEntity<Map<String, String>> deleteBoard(@PathVariable int bbsseq) {
        log.debug("deleteBoard: {}.", bbsseq);
        // 게시판 삭제
        boolean isSuccess = service.deleteBoard(bbsseq);
        if (isSuccess) {
            HashMap<String, String> result = new HashMap<>();
            result.put("message", "게시물 삭제 성공");
            String timestamp = String.valueOf(LocalDateTime.now());
            result.put("timestamp", timestamp);
            result.put("status", String.valueOf(HttpStatus.OK.value()));

            return ResponseEntity.ok(result);
        } else {
            HashMap<String, String> result = new HashMap<>();
            result.put("message", "게시물 삭제 실패");
            String timestamp = String.valueOf(LocalDateTime.now());
            result.put("timestamp", timestamp);
            result.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }


    @GetMapping("/bbslist")         // Read    // Null 가능 // lombok DefaultValue
    public Map boardList(@RequestBody SearchParam searchparam){
//        log.debug("boardParam : {} {} {}.", search, pageNum, exposedCount);

        // 게시판 목록 조회    // default : search = "", pageNum = 1, exposedCount = 5개 씩..
        // String 으로 받아야 예외처리를 유연하게 가능 (int 로 받을시 문제될 경우 runtimeException 으로 빠져버림)

        log.debug("boardList : {}.", searchparam);





        // pageNum = 입력받은 값 과 pageNum을 계산해서 최대 페이지 비교 후 더 크다면 최대 페이지로   // 음수 입력시 예외처리




        // Map으로 search 들에 대해서 return (totalCount, pageNum, exposedCount, bbsList)

        return service.boardList(searchparam);
    }



}
