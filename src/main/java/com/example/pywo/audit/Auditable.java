package com.example.pywo.audit;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@Slf4j
@Data
@EntityListeners(AuditingEntityListener.class)
public class Auditable<T> {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    protected Date creationDate;


    @Column(name = "modified_at")
    @LastModifiedDate
    protected Date lastModifiedDate;

}
