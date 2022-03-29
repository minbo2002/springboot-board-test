package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  // JPA가 Entity 어노테이션을 확인하고 아래 데이터를 처리. JPA에서 클래스의 이름은 테이블 이름을 뜻함.
@Data
public class Board {

    @Id  // PK 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // GenerationType.IDENTITY : MySQL or MariaDB에서 사용
    private Integer id;                                   // GenerationType.SEQUENCE : Oracle에서 사용

    private String title;

    private String content;

    private String filename;

    private String filepath;
}
