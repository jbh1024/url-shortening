package org.iptime.hoonyhoony.urlshortening.service;


import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;

import java.util.List;

public interface UrlShorteningService {
    String getOriginUrlByAlias(String alias);

    String generateShorteningUrl(String originUrl);

    List<UrlInfo> getAllUrlShortening();
}
