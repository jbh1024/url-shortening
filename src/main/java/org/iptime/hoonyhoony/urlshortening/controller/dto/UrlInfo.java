package org.iptime.hoonyhoony.urlshortening.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
* 리뷰 후 어노테이션 변경 - Hash나 toString 은 사용하지않음으로.
 * @Data -> @Getter, @Setter
 * @Data*/
/*
@Data 롬복에서 가장 중요한 어노테이션 중 하나.
 @ToString, @EqualsAndHashCode, @Getter/@Setter, @RequiredArgsConstructor 을 모두 사용한 것과 같은 효과.
 @RequiredArgsConstructor 어노테이션으로 만들어지는 생성자는 다른 생성자가 없을 때에만 만들어진다.
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
    private int id;
    private String shorteningUrl;
    private String originUrl;
    private Date createDate;

}
