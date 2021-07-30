package com.example.springdata_jpa.repository;

import com.example.springdata_jpa.entity.Member;
import com.example.springdata_jpa.entity.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query(name = "Member.findByMemberName")
    List<Member> findByMemberName(@Param("memberName") String memberName);

    /**
     * Interface에 Query를 바로 정의
     * */
    @Query("select m from Member m where m.memberName = :memberName and m.age=:age")
    List<Member> findMember(@Param("memberName") String memberName, @Param("age") String age);

    @Query("select m.memberName from Member m")
    List<String> findMemberNameList();

    @Query("select new com.example.springdata_jpa.entity.MemberDto(m.id, m.memberName, t.teamName) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.memberName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    /**
     * 단건일 경우 null을 반환하지만 복수일 경우 no result exception이 발생한다.
     * Optional은 클라이언트에게 코드의 책임을 묻는다. 단, 단건조회에서 두개가 조회되면 터진다
     * */
    List<Member> findListByMemberName(String memberName);
    Optional<Member> findMemberByMemberName(String memberName);

//    Slice<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join  m.team t")
    Page<Member> findByAge(int age, Pageable pageable);
//    @Query(value = "select m from Member m left join  m.team t", countQuery = "select count(m.memberName) from Member m")
//    Page<Member> findByAge(int age, Pageable pageable);

}
