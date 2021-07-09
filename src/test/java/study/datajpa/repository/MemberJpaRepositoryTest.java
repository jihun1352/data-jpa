package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        //given
        Member member = new Member("memberA");
        Member saveMember = memberJpaRepository.save(member);

        //when
        Member findMember = memberJpaRepository.find(saveMember.getId());

        //then
        Assertions.assertThat(saveMember.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(saveMember.getUsername()).isEqualTo(findMember.getUsername());
        Assertions.assertThat(saveMember).isEqualTo(findMember);
    }


}