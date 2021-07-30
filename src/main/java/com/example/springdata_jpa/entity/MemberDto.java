package com.example.springdata_jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberDto {

    private Long id;
    private String memberName;
    private String teamName;

    public MemberDto(Long id, String memberName, String teamName) {
        this.id = id;
        this.memberName = memberName;
        this.teamName = teamName;
    }
}
