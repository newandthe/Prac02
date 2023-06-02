package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.service.Prac02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @PostMapping("")        // Create
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

    @GetMapping("/{bbsseq}")     // Read
    public Board getBoard(@PathVariable int bbsseq) {
        log.debug("getBoard bbsseq : {}.", bbsseq);
        // 게시판 상세보기
        return service.getBoard(bbsseq);
    }


    // PathVariable의 경우 service에 값이 존재하는지 파악하고 없으면 trhow
    @PutMapping("/{bbsseq}")        // Update
    public String reWriteBoard(@RequestBody Board board, @PathVariable int bbsseq){
        log.debug("reWriteBoard Board : {}.", board);
        // 게시판 수정
        boolean isSuccess = service.reWriteBoard(board, bbsseq);

        if(isSuccess){
            return "OK";
        }
        return "null";
    }

    @DeleteMapping("/{bbsseq}")     // Delete
    public String deleteBoard(@PathVariable int bbsseq){
        log.debug("deleteBoard : {}.", bbsseq);
        // 게시판 삭제
        boolean isSuccess = service.deleteBoard(bbsseq);
        if(isSuccess){
            return "OK";
        }
        return "null";
    }

    @GetMapping("/bbslist")         // Read         // defaultValue를 설정하기위해 RequestBody 사용 보류 // Null 가능
    public Map boardList(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                            @RequestParam(value = "exposedCount", required = false, defaultValue = "5") int exposedCount){

        // 게시판 목록 조회    // default : search = "", pageNum = 1, exposedCount = 5개 씩..
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(search);
        searchParam.setPageNum(pageNum);
        searchParam.setExposedCount(exposedCount);
        log.debug("boardList : {}.", searchParam);


        // List<Board> boardlist = service.boardList(searchParam);  // Map으로 보내기 (searchParam과 totalCount)
        // boardlist가 없는경우 예외처리 추가예정.. (jdbctemplate)



        // pageNum = 입력받은 값 과 pageNum을 계산해서 최대 페이지 비교 후 더 크다면 최대 페이지로   // 음수 입력시 예외처리




        // Map으로 search 들에 대해서 return (totalCount, pageNum, exposedCount, bbsList)
        System.out.println("search : " + search);
        System.out.println("pageNum : " + pageNum);
        System.out.println("exposedCount : " + exposedCount);

        return service.boardList(searchParam);
        // return boardlist;
    }



}
