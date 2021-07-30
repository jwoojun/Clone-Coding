package com.example.springdata_jpa;

import com.example.springdata_jpa.entity.Member;
import com.example.springdata_jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    /**
    *동일한 트랜잭션이기 때문에 같다.
    * */
    @Test
    public void testMember(){
        Member member = new Member(null, "memberA");
        memberRepository.save(member);

        Member findMember = memberRepository.findmaxage(member.getAge());
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member); // findMember == member -> equals override 하지 않았기 때문
    }
}