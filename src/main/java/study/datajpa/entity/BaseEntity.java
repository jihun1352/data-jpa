package study.datajpa.entity;

import lombok.Getter;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {
    @CreatedDate    //생성일
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate   //수정일
    private LocalDateTime lastModifiedDate;

    @CreatedBy      //생성자
    @Column(updatable = false)
    private String createdBy;
    @LastModifiedBy //수정자
    private String lastModifiedBy;
}
