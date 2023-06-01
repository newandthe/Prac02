package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.repository.Prac02Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
