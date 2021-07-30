package com.example.springdata_jpa.repository;

import com.example.springdata_jpa.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member findmaxage(int age) {
        Member findmember = em.createQuery("select m from Member m where m.age=(select max(age) from m)", Member.class)
                .getSingleResult();
        return findmember;
    }

    public Member containstring(String str) {

        List<Member> findmembers = em.createQuery("select m from Member m where m.age between 10 and 40", Member.class)
                .getResultList();
        List<Member> findmembers2 = em.createQuery("select m from Member m where m.memberName like concat('%', :str, '%')", Member.class)
                .setParameter("str", str)
                .getResultList();
        List<Member>result = em.createQuery("select m from Member m join m.team t where t.teamName='teama'", Member.class).getResultList();
        result.forEach(System.out::println);
        return null;
    }

//    public List<Member> findbyusername(String  username) {
//    return em.createNamedQuery("Member.findByUsername", Member.class)
//            .setParameter("username", "회원1")
//            .getResultList();
//    }

    public List<Member> findByPage(int age, int offset, int limit){
        return em.createQuery("select m from Member m where m.age = :age order by m.memberName desc ")
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();


    }


    /**
     * 몇 개부터(offset), 몇 개를(limit) 지정해서 이를 넘겨준다.
     * */
    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }
}
