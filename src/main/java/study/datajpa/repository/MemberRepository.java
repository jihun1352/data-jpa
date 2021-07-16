package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import study.datajpa.dto.MemberDto;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    //@Query(name = "Member.findByUsername")
    List<Member> findByUsername(String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //new operation 사용. (추후 쿼리dsl 사용시 패키지명을 입력하지 않아도 된다.)
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //컬렉션 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    //컬렉션 조회
    List<Member> findListByUsername(String name);
    //단건 조회
    Member findMemberByUsername(String name);
    //단건optional 조회
    Optional<Member> findOptionalByUsername(String name);

    //메소드 이름으로 쿼리생성
    //Page 반환 : count 쿼리 동작
    Page<Member> findPageByAge(int age, Pageable pageable);
    //Slice 반환 : count 쿼리 x
    Slice<Member> findSliceByAge(int age, Pageable pageable);
    //List 반환 : count 쿼리 x
    List<Member> findListByAge(int age, Pageable pageable);

    //count 쿼리 분리
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findCountByAge(int age, Pageable pageable);

    //벌크성 수정
    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //fetch join
    @Query("select m from Member m left join fetch m.team t")
    List<Member> findMemberFetchJoin();

    //엔티티 그래프
    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();
    //메서드 이름으로 쿼리에서 특히 편하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(String username);

    //쿼리 힌트
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    //쿼리 힌트 페이징 테스트
    @QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")}, forCounting = true)
    Page<Member> findQueryHintByUsername(String username, Pageable pageable);

    //Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    //projections
    <T> List<T> findProjectionsByUsername(String username, Class<T> type);

    //네이티브 쿼리
    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id, m.username, t.name as teamName " +
            "from member m left join team t",
        countQuery = "select count(*) from member",
        nativeQuery = true)
    Page<MemberProjection> findByNativeQueryProjection(Pageable pageable);
}
