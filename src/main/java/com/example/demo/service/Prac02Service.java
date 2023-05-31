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

    public Board createBoard(Board board){
        // 게시판 등록 로직 처리
        return repository.save(board);
    }

    public Board getBoard(int bbsseq) {
        // 게시판 상세보기 로직 처리
        return repository.findById(bbsseq);
    }

}
