package com.example.demo.config;

import com.example.demo.model.Board;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardRowMapper implements RowMapper<Board> {

    @Override
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException{
        Board board = new Board();
        board.setBbsseq(rs.getInt("bbsseq"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setDel(rs.getInt("del"));
        return board;
    }
}
