package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired  // 객체생성하지않고 spring bean이 자동으로 읽어서 주입해줌 (DI)
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board) {

        boardRepository.save(board);   // JPA의 .save() 메서드
    }

    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();  // JPA의 .findAll() : List내부의 제네릭인 Board 클래스를 반환
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
        // findById로 값을 찾으면 Optional클래스에 감싸져 있으므로
        // 그 안에 있는 Board 클래스를 꺼내려면 .get()을 사용.
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
