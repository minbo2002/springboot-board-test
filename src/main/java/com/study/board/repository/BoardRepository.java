package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // JpaRepository<Entity 클래스이름, PK의 type>

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

    // findBy(컬럼이름)Containing  :  JPA Repository에서 제공하는 검색기능 키워드
}
