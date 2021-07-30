package com.example.springdata_jpa;

import com.example.springdata_jpa.entity.Member;
import com.example.springdata_jpa.entity.MemberDto;
import com.example.springdata_jpa.entity.Team;
import com.example.springdata_jpa.repository.MemberJpaRepository;
import com.example.springdata_jpa.repository.MemberRepository;
import com.example.springdata_jpa.repository.TeamJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Rollback(value = false)
@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    TeamJpaRepository teamJpaRepository;


    /**
     * 구현체(Proxy)를 스프링이 만들어준다.
     * Repository애노테이션을 생략해도 된다.
     * @Repository는 JPA의 예외를 Spring에서 처리할 수 있는 공통적인 예외로 만드는 역할도 한다.
     */
    @Test
    void testJPA() {
        System.out.println("=======================================");
//        Member memberA = new Member(10,"AAA", null);
//        memberJpaRepository.save(memberA);

        System.out.println("=======================================");
//        List<Member> result = memberJpaRepository.findbyusername("AAA");
//        Member findMember = result.get(0);
//        System.out.println(result);
//        assertThat(findMember).isEqualTo(memberA);
    }
    @Test
    void insert_data(){
        Team teamA = new Team("teamA");
        teamJpaRepository.save(teamA);
        Member memberA = new Member(20,"Jung", teamA);
        memberRepository.save(memberA);

    }

    @Test
    void findMaxAge(){
    }

    @Test
    void findContains(){
        Member findMember=  memberRepository.containstring("member");
        System.out.println("=======================================");
        System.out.println(findMember);
        System.out.println("=======================================");
    }


    @Test
    void namedQuery(){
        Member findMember = memberJpaRepository.findByMemberName("AAA").get(0);
        assertThat(findMember.getMemberName()).isEqualTo("AAA");
    }

    @Test
    void queryMethod_First(){
        Team teamA = new Team("teamA");
        teamJpaRepository.save(teamA);
        Member memberA = new Member(50, "Jang", teamA);
        memberJpaRepository.save(memberA);
        Member findMember = memberJpaRepository.findByMemberName("Jang").get(0);
        assertThat(findMember.getMemberName()).isEqualTo("Jang");
    }

    @Test
    void queryMethod_Second(){
        memberJpaRepository.findMemberNameList().forEach(System.out::println);
    }

    @Test
    void queryMethod_Third(){
        memberJpaRepository.findMemberDto().forEach(System.out::println);

    }

    @Test
    void queryMethod_Fourth(){
        memberJpaRepository.findByNames(Arrays.asList("AAA", "Jang")).forEach(System.out::println);

    }
    /**
     *
     * */
    @Test
    void queryMethod_Fifth(){
            memberJpaRepository.findMemberByMemberName("Jung");
    }

    @Test
    void paging_data(){
        Team teamA = new Team("teamA");
        teamJpaRepository.save(teamA);
        Random random = new Random();

        List<String> names = Arrays.asList("Jung", "Jang", "Kim", "Hwang");
        for(int i=0; i<3000; i++){
            memberJpaRepository.save(new Member(random.nextInt(10)+30,names.get(random.nextInt(3)) , teamA));
        }

    }


    @Test
    void paging_First(){
        int age = 10;
        int offset = 0;
        int limit = 3;
        List<Member> members = memberRepository.findByPage(age,offset,limit);
        long totalCount = memberRepository.totalCount(10);
        assertThat(members.size()).isEqualTo(totalCount);
    }

    @Test
    void paging_Second(){
        int age = 30;
        PageRequest pageRequest = PageRequest.of(0, 10);

        /**
         * PageFhgkaus
         * */
//        Page<Member> page = memberJpaRepository.findByAge(age, pageRequest);

//        List<Member> content = page.getContent();
//        long totalElements = page.getTotalElements();
//        assertThat(page.getTotalElements()).isEqualTo(totalElements);
//        assertThat(page.isFirst()).isTrue();
//        System.out.println("========================================================");
//        assertThat(page.hasNext()).isTrue();
//        System.out.println("content= "+content+ " total= "+totalElements);
    }


//    @Test
//    void paging_Third(){
//        int age = 30;
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        /**
//         * 전체 카운트를 계산하지 않는다.
//         * 내가 요청한 것보다 1개 더 요청한다.
//         * */
//        Slice<Member> page = memberJpaRepository.findByAge(age, pageRequest);
//
//        List<Member> content = page.getContent();
//        assertThat(page.isFirst()).isTrue();
//        System.out.println("========================================================");
//        Page<MemberDto> toMap = page.map(member->new MemberDto(member.getId(), member.getMemberName(), null));
//        assertThat(page.hasNext()).isTrue();
////        System.out.println("content= "+content+ " total= "+totalElements);
//    }

    @Test
    void paging_Fourth(){
        int age = 30;
        PageRequest pageRequest = PageRequest.of(0, 10);

        /**
         * 전체 카운트를 계산하지 않는다.
         * 내가 요청한 것보다 1개 더 요청한다.
         * */
        Page<Member> page = memberJpaRepository.findByAge(age, pageRequest);

        List<Member> content = page.getContent();
        assertThat(page.isFirst()).isTrue();
        System.out.println("========================================================");
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getMemberName(), null));
        assertThat(page.hasNext()).isTrue();
//        System.out.println("content= "+content+ " total= "+totalElements);
    }
}