package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller   // spring이 실행될때 Controller라고 인식함
public class BoardController {

    @Autowired  // 객체생성하지않고 spring bean이 자동으로 읽어서 주입해줌 (DI)
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardwritePro(Board board, Model model, MultipartFile file) throws Exception {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id",
                                    direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {
        // Pageable은 인터페이스
        // page : dafault 페이지   size : 한페이지 게시글 수
        // sort : 정렬기준 컬럼     direction : 정렬 순서 (ASC오름차순, DESC내림차순)

        Page<Board> list = null;
        // Page는 인터페이스

        if(searchKeyword == null) {  // 검색할 단어 없는경우 이전 board 그대로 보여줌

            list = boardService.boardList(pageable);

        }else {  //  검색할 단어가 포함된 board를 보여줌

            list = boardService.boardSearchList(searchKeyword, pageable);
        }


        int nowPage = list.getPageable().getPageNumber() + 1;  // Pageable 인터페이스에서 넘어온 현재페이지를 가져올수 있다.
        // Pageable 인터페이스는 첫페이지가 0부터 시작하기 때문에 우리가 보는 첫페이지를 1페이지부터 시작하게 하려면 +1 해줘야함

        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, list.getTotalPages());  // 원래 페이지를 뛰어넘는 페이지에 못가도록 Math.min() 메서드 사용

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")  // localhost:8080//board/view?id=1
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model) {

        boardService.boardDelete(id);

        model.addAttribute("message", "글이 삭제되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        // return "redirect:/board/list";
        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        // url의 modify의 뒤쪽 {id}가 @PathVariable("id") 에 인식이 된다음 Integer id로 들어온다는 의미
        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception {

        Board boardTemp = boardService.boardView(id);  // 기존에 있던 내용 가지고오고
        boardTemp.setTitle(board.getTitle());  // 수정한 title 덮어씌운다
        boardTemp.setContent(board.getContent());  // 수정한 content 덮어씌운다

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}
