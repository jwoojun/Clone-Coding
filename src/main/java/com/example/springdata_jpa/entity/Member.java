package com.example.springdata_jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@NamedQuery(name = "Member.findByMemberName", query = "select m from Member m where m.memberName =:memberName")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private int age;

    @Column(name = "memberName")
    private String memberName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members")
    @ToString.Exclude
    private Team team;

    public Member(Long id, String membername) {
        this.id = id;
        this.memberName = membername;
    }

    public Member(int age, String memberName, Team team) {
        this.age = age;
        this.memberName = memberName;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
