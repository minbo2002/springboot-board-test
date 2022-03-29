package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired  // 객체생성하지않고 spring bean이 자동으로 읽어서 주입해줌 (DI)
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        // 파일 저장할 경로 지정

        UUID uuid = UUID.randomUUID();  // 파일이름에 붙일 랜덤식별자 지정

        String fileName = uuid + "_" + file.getOriginalFilename();  // 랜덤식별자_파일이름
        System.out.println("파일 이름 : " + fileName);

        File saveFile = new File(projectPath, fileName);  // File(경로지정, 경로이름)

        file.transferTo(saveFile);

        board.setFilename(fileName);  // 저장된 파일이름
        board.setFilepath("/files/" + fileName);  // 저장된 파일의 경로 및 이름

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
