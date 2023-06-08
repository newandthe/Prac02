package com.example.demo.repository;

import com.example.demo.config.BoardRowMapper;
import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class Prac02Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Prac02Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean createBoard(Board board) {
        try {
            // 게시판 등록을 위한 SQL 작성
            String sql = "INSERT INTO boards (title, content) VALUES (?, ?)";
            int n = jdbcTemplate.update(sql, board.getTitle(), board.getContent());

            System.out.println("n " + n);

            return n > 0;
        } catch (DataAccessException ex) {
            // 데이터 액세스 예외 처리
            // 예외 처리 로직을 추가해서 응답을 반환하거나 로깅 수행
            throw new RuntimeException("게시판 등록에 실패했습니다.", ex);
        }
    }

    public Board findById(int bbsseq) {
        // 게시판 상세보기를 위한 SQL 작성
        String sql = "SELECT * FROM boards WHERE bbsseq = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{bbsseq}, new BoardRowMapper());
        // queryForObject 는 결과가 없으면 EmptyResultDataAccessException 예외 발생
        // try catch로 변경
        // https://sasca37.tistory.com/219

    }

    public boolean reWriteBoard(Board board, int bbsseq) {
        try {
            String sql = "UPDATE boards SET title = ?, content = ? WHERE bbsseq = ?";
            int n = jdbcTemplate.update(sql, board.getTitle(), board.getContent(), bbsseq);
            return n > 0;
        } catch (DataAccessException ex) {
            throw new RuntimeException("게시판 수정에 실패했습니다.", ex);
        }
    }


    public boolean deleteBoard(int bbsseq) {
        try {
            // 보통은 update로 del 컬럼을 1로 수정해주는 방식을 해왔지만 DELETE 했습니다.
            String sql = "DELETE FROM boards WHERE bbsseq = ?";
            int n = jdbcTemplate.update(sql, bbsseq);
            return n > 0;
        } catch (DataAccessException ex) {
            throw new RuntimeException("게시판 삭제에 실패했습니다.", ex);
        }
    }

    public List<Board> boardList(SearchParam search) {
        // pageNum에 따른 출력 범위
        int offset = search.getExposedCount() * (search.getPageNum() - 1);


        String sql = "SELECT * FROM boards WHERE del = 0 AND title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%') LIMIT ? OFFSET ?";
//        return jdbcTemplate.query(sql, search, search, search.getExposedCount(), offset);    // 당장은 동적쿼리로 만들 필요 X
        System.out.println(search.getExposedCount()* (search.getPageNum()-1));
        System.out.println(search.getPageNum());
        List<Board> result = jdbcTemplate.query(sql, new BoardRowMapper(), search.getSearch(), search.getSearch(), search.getExposedCount(), offset );


        return result.isEmpty() ? null:result;   // 해당 검색어로 조회되지 않는 경우 null return
    }

    // public class ~~

    public int getTotalCount(String search) {
        String sql = "SELECT count(*) FROM boards WHERE title LIKE CONCAT('%', ?, '%') OR content LIKE CONCAT('%', ?, '%')";


        // null인경우 EmptyResultDataAccessException try catch 문으로 예외처리
        return jdbcTemplate.queryForObject(sql, Integer.class, search, search);

    }

    public int isExists(int bbsseq){
        String sql = "SELECT count(*) FROM boards WHERE bbsseq = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, bbsseq);
    }

    // 목록 조회 (검색 정렬은 동적쿼리가 필요하다.)
}
