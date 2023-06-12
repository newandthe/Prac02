package com.example.demo.service;

import com.example.demo.model.Board;
import com.example.demo.model.SearchParam;
import com.example.demo.repository.Prac02Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class Prac02Service {

    private final Prac02Repository repository;

    @Autowired
    public Prac02Service(Prac02Repository repository){
        this.repository = repository;
    }

    public boolean createBoard(Board board){
        // 게시판 등록 로직 처리
        if (board.getTitle().length() > 100) {
            throw new DataIntegrityViolationException("제목(title)의 길이가 너무 깁니다.");
        } else if (board.getContent().length() > 2000) {
            throw new DataIntegrityViolationException("내용(content)의 길이가 너무 깁니다.");
        }

        return repository.createBoard(board);
    }

    public Board getBoard(int bbsseq) {
        // 게시판 상세보기 로직 처리
        return repository.findById(bbsseq);
    }

    public boolean reWriteBoard(Board board, int bbsseq) {
        int n = repository.isExists(bbsseq);
        if(n == 0){
            log.debug("reWriteBoardService : {}", bbsseq);
            throw new EmptyResultDataAccessException(1);   // expected Size
        }

        // 게시판 수정 로직 처리
        if (board.getTitle().length() > 100) {
            throw new DataIntegrityViolationException("제목(title)의 길이가 너무 깁니다.");
        } else if (board.getContent().length() > 2000) {
            throw new DataIntegrityViolationException("내용(content)의 길이가 너무 깁니다.");
        }
        return repository.reWriteBoard(board, bbsseq);
    }

    public boolean deleteBoard(int bbsseq) {
        int n = repository.isExists(bbsseq);
        if(n == 0){
            log.debug("reWriteBoardService : {}", bbsseq);
            throw new EmptyResultDataAccessException(1);   // expected Size
        }

        // 게시판 삭제 로직 처리
        return repository.deleteBoard(bbsseq);
    }

    public HashMap<Object, Object> boardList(SearchParam search) {
        // 게시판 검색 로직 처리


        // exposedCount는 사용자가 입력한 값 중 최대 100으로 수정 (리스트를 최대 100개까지만 return)  // 음수 입력시 예외처리 or 강제로 1로 정정
        if(search.getExposedCount()>100){
            search.setExposedCount(100);
        } else if(search.getExposedCount()<1){
            search.setExposedCount(1);
        }

        // pageNum은 totalCount를 통해 노출 할 수 있는 최대 값을 넘어가면 값이 나올 수 있는 페이지로 설정..
        // totalCount = search 에 대한 검색결과 개수 쿼리문
        int totalCount = repository.getTotalCount(search.getSearch());
        HashMap<Object, Object> result = new HashMap<>();

        if(totalCount == 0){    // 검색결과가 없다면 null로 보내고 끝.

            result.put("searchParam", search);
            result.put("totalCount", totalCount);
            result.put("bbsList", null);

            return result;
        }


        // totalCount가 0이 아닌경우이므로 결과가 존재한다는 의미.
        if(  (search.getPageNum() > (int)Math.ceil( (double) totalCount/search.getExposedCount())) ){   // 요청받은 페이지가 요구조건으로 표현할수 있는 페이지보다 크다면..
            search.setPageNum(  (int)Math.ceil( (double) totalCount/search.getExposedCount())  ); // 마지막 페이지로 강제로 변경
        } else if (search.getPageNum()<1){ // 사용자가 설정한 페이지가 음수라면 1페이지로 강제 변경
            search.setPageNum(1);
        }

        System.out.println("search.pagenum" + search.getPageNum());
        List<Board> bbslist = repository.boardList(search);



        result.put("totalCount", totalCount);
        result.put("bbsList", bbslist);
        result.put("searchParam", search);

        return result;
    }


}
