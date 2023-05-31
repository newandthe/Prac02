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

    public Board save(Board board) {
        // 게시판 등록을 위한 SQL 작성
        String sql = "INSERT INTO boards (title, content) VALUES (?, ?)";
        jdbcTemplate.update(sql, board.getTitle(), board.getContent());

        // 등록된 게시물의 ID 조회
        String idQuery = "SELECT LAST_INSERT_ID()";
        int generatedId = jdbcTemplate.queryForObject(idQuery, Integer.class);

        board.setBbsseq(generatedId);
        return board;
    }

    public Board findById(int bbsseq) {
        // 게시판 상세보기를 위한 SQL 작성
        String sql = "SELECT * FROM boards WHERE bbsseq = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{bbsseq}, new BoardRowMapper());
    }
}
