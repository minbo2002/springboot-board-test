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

    public void write(Board board) {

        boardRepository.save(board);   // JPA의 .save() 메서드
    }

    public List<Board> boardList() {

        return boardRepository.findAll();  // JPA의 .findAll() : List내부의 제네릭인 Board 클래스를 반환
    }
}
