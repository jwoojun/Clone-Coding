package com.example.springdata_jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    @OneToMany
    List<Member> members = new ArrayList<>();

    protected Team() {};
}
