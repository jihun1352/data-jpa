package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //엔티티에서 DTO로는 바라보지 않게하고 DTO에서 엔티티로 바라보는 것이 좋다.
    public MemberDto(Member m) {
        this.id = m.getId();
        this.username = m.getUsername();
    }
}
