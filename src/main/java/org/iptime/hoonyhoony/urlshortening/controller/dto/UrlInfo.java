package org.iptime.hoonyhoony.urlshortening.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
    private int id;
    private String shorteningUrl;
    private String originUrl;
    private Date createDate;

}
