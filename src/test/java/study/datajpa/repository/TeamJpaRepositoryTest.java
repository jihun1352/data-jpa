package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Team;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class TeamJpaRepositoryTest {

    @Autowired
    TeamJpaRepository teamJpaRepository;

    @Test
    public void baseCRUD() {
        Team team1 = new Team("teamA");
        Team team2 = new Team("teamB");
        teamJpaRepository.save(team1);
        teamJpaRepository.save(team2);

        //단건 조회 검증
        Team findTeam1 = teamJpaRepository.findById(team1.getId()).get();
        Team findTeam2 = teamJpaRepository.findById(team2.getId()).get();
        Assertions.assertThat(findTeam1).isEqualTo(team1);
        Assertions.assertThat(findTeam2).isEqualTo(team2);

        //리스트 조회 검증
        List<Team> teamList = teamJpaRepository.findAll();
        Assertions.assertThat(teamList.size()).isEqualTo(2);

        //카운트 검증
        long count = teamJpaRepository.count();
        Assertions.assertThat(count).isEqualTo(2);
        
        //수정
        team1.setName("newTeam!!!");
        System.out.println("team1.getName() = " + team1.getName());

        //삭제 검증
        teamJpaRepository.delete(team1);
        teamJpaRepository.delete(team2);
        long deletedCount = teamJpaRepository.count();
        Assertions.assertThat(deletedCount).isEqualTo(0);
    }
}