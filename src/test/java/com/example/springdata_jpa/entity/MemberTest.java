package com.example.springdata_jpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@SpringBootTest
@Transactional
class MemberTest {
        @PersistenceContext
        EntityManager em;


        @Test
        @Commit
        public void testEntity() {
            Team teamA = new Team("teamA");
            Team teamB = new Team("teamB");
            em.persist(teamA);
            em.persist(teamB);

            Member memberA = new Member(10, "memberA", teamA);
            Member memberB = new Member(18, "memberB", teamB);
            Member memberB2 = new Member(38, "memberB2", teamB);
            Member memberA2 = new Member(28, "memberA2", teamA);
            em.persist(memberA);
            em.persist(memberA2);
            em.persist(memberB);
            em.persist(memberB2);
            em.flush();
            em.clear(); // JPA의 영속성 컨텍스트를 초기화

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member= " + member);
            }

        }
}