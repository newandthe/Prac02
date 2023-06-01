package com.example.demo.repository;

import com.example.demo.config.BoardRowMapper;
import com.example.demo.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Prac02Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Prac02Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean createBoard(Board board) {
        // 게시판 등록을 위한 SQL 작성
        String sql = "INSERT INTO boards (title, content) VALUES (?, ?)";
        int n = jdbcTemplate.update(sql, board.getTitle(), board.getContent());

        System.out.println("n " + n);

        return n>0?true:false;
    }

    public Board findById(int bbsseq) {
        // 게시판 상세보기를 위한 SQL 작성
        String sql = "SELECT * FROM boards WHERE bbsseq = ?";

        // queryForObject 는 결과가 없으면 EmptyResultDataAccessException 예외 발생
        // try catch로 변경해야..
        // https://sasca37.tistory.com/219
        return jdbcTemplate.queryForObject(sql, new Object[]{bbsseq}, new BoardRowMapper());
    }

    public boolean reWriteBoard(Board board, int bbsseq) {
        String sql = "UPDATE boards SET title = ?, content = ? WHERE bbsseq = ?";
        int n = jdbcTemplate.update(sql, board.getTitle(), board.getContent(), bbsseq);
        return n>0?true:false;
    }


    public boolean deleteBoard(int bbsseq) {
        String sql = "DELETE FROM boards WHERE bbsseq = ?";
        int n = jdbcTemplate.update(sql, bbsseq);
        return n>0?true:false;
    }

    // 목록 조회 (검색 정렬은 동적쿼리가 필요하다.)
}
