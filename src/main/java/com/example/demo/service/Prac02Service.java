package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.repository.Prac02Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Prac02Service {

    private final Prac02Repository repository;

    @Autowired
    public Prac02Service(Prac02Repository repository){
        this.repository = repository;
    }

    public boolean createBoard(Board board){
        // 게시판 등록 로직 처리
        return repository.createBoard(board);
    }

    public Board getBoard(int bbsseq) {
        // 게시판 상세보기 로직 처리
        return repository.findById(bbsseq);
    }

    public boolean reWriteBoard(Board board, int bbsseq) {
        // 게시판 수정 로직 처리
        return repository.reWriteBoard(board, bbsseq);
    }

    public boolean deleteBoard(int bbsseq) {
        // 게시판 삭제 로직 처리
        return repository.deleteBoard(bbsseq);
    }

    public HashMap<Object, Object> boardList(SearchParam search) {
        // 게시판 검색 로직 처리

        // exposedCount는 사용자가 입력한 값 중 최대 100으로 수정 (리스트를 최대 100개까지만 return)  // 음수 입력시 예외처리 or 강제로 5로 정정
        if(search.getExposedCount()>100){
            search.setExposedCount(100);
        }

        // totalCount = search 에 대한 검색결과 개수 쿼리문
        int totalCount = repository.getTotalCount(search.getSearch());
        List<Board> bbslist = repository.boardList(search);

        HashMap<Object, Object> result = new HashMap<>();
        result.put("searchparam", search);
        result.put("bbslist", bbslist);

        return result;
    }


}
