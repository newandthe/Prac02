package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.service.Prac02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/")        // Create
    public String createBoard(@Valid @RequestBody Board board) {
        log.debug("createBoard Board : {}.", board);


        // 게시판 등록
        boolean isSuccess = service.createBoard(board);

        if(isSuccess){
            return "게시물 등록 성공";
        }
        return "게시물 등록 실패";
    }

    @GetMapping("/{bbsseq}")     // Read
    public Board getBoard(@PathVariable int bbsseq) {
        log.debug("getBoard bbsseq : {}.", bbsseq);
        // 게시판 상세보기
        return service.getBoard(bbsseq);
    }



    @PutMapping("/{bbsseq}")        // Update
    public String reWriteBoard(@Valid @RequestBody Board board, @PathVariable int bbsseq){
        log.debug("reWriteBoard Board : {}.", board);

        // 게시판 수정
        boolean isSuccess = service.reWriteBoard(board, bbsseq);

        if(isSuccess){
            return "수정 성공";
        }
        return "Fail";
    }


    // 존재하는지 먼저 파악한후 throw
    @DeleteMapping("/{bbsseq}")     // Delete
    public String deleteBoard(@PathVariable int bbsseq){
        log.debug("deleteBoard : {}.", bbsseq);
        // 게시판 삭제
        boolean isSuccess = service.deleteBoard(bbsseq);
        if(isSuccess){
            return "OK";
        }
        return "Fail";
    }

    @GetMapping("/bbslist")         // Read         // defaultValue를 설정하기위해 RequestBody 사용 보류 // Null 가능
    public Map boardList(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                            @RequestParam(value = "exposedCount", required = false, defaultValue = "5") String exposedCount){
//        log.debug("boardParam : {} {} {}.", search, pageNum, exposedCount);

        // 게시판 목록 조회    // default : search = "", pageNum = 1, exposedCount = 5개 씩..
        // String 으로 받아야 예외처리를 유연하게 가능 (int 로 받을시 문제될 경우 runtimeException 으로 빠져버림)
        int page = Integer.parseInt(pageNum);
        int exposed = Integer.parseInt(exposedCount);

        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(search);
        searchParam.setPageNum(page);
        searchParam.setExposedCount(exposed);
        log.debug("boardList : {}.", searchParam);





        // pageNum = 입력받은 값 과 pageNum을 계산해서 최대 페이지 비교 후 더 크다면 최대 페이지로   // 음수 입력시 예외처리




        // Map으로 search 들에 대해서 return (totalCount, pageNum, exposedCount, bbsList)

        return service.boardList(searchParam);
    }



}
