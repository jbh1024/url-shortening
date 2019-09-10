package org.iptime.hoonyhoony.urlshortening.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * UrlShortening 객체
 * */

@Entity
@Data
@Table(name = "url_info")
public class UrlShorteningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String alias;

    @Column
    private String originUrl;

    @Column
    @CreatedDate
    private Date createDate = new Date();

    @Column
    @LastModifiedDate
    private Date updateDate = new Date();

}
