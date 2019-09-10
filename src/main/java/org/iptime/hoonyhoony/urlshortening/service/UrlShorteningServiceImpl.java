package org.iptime.hoonyhoony.urlshortening.service;

import org.iptime.hoonyhoony.urlshortening.common.HashingUtil;
import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;
import org.iptime.hoonyhoony.urlshortening.exception.CustomException;
import org.iptime.hoonyhoony.urlshortening.repository.UrlShorteningRepository;
import org.iptime.hoonyhoony.urlshortening.repository.entity.UrlShorteningEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.iptime.hoonyhoony.urlshortening.exception.ErrorCode.CREATION_ERROR;

/**
 * UrlShorteningService 구현부
 **/
@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {
    private final static int GENERATE_COUNT = 5; //해시코드 충돌시 재생성 횟수

    @Autowired
    private UrlShorteningRepository urlShorteningRepository;

    @Override
    @Transactional
    public String getOriginUrlByAlias(String alias) {
        String originUrl = null;
        Optional<UrlShorteningEntity> urlShorteningEntity = urlShorteningRepository.findFirstByAlias(alias);
        if (urlShorteningEntity.isPresent())
            originUrl = urlShorteningEntity.get().getOriginUrl();
        return originUrl;
    }


    @Override
    @Transactional
    public String generateShorteningUrl(String originUrl) {
        Optional<UrlShorteningEntity> urlShorteningEntity = urlShorteningRepository.findFirstByOriginUrl(originUrl);
        if (urlShorteningEntity.isPresent()) {
            //생성 요청한 원본 url이 존재 할 경우 기존에 생성된 alias 로 제공
            return urlShorteningEntity.get().getAlias();
        }
        return generate(originUrl);
    }

    private String generate(String originUrl) {
        String shorteningUrl;
        for (int i = 0; i < GENERATE_COUNT; i++) {
            shorteningUrl = HashingUtil.generateHashingAlias(originUrl + i);
            if (!urlShorteningRepository.findFirstByAlias(shorteningUrl).isPresent() && shorteningUrl.length() < 8) {
                UrlShorteningEntity urlShorteningEntity = new UrlShorteningEntity();
                urlShorteningEntity.setOriginUrl(originUrl);
                urlShorteningEntity.setAlias(shorteningUrl);
                urlShorteningRepository.save(urlShorteningEntity);
                return shorteningUrl;
            }
        }
        throw new CustomException(CREATION_ERROR);
    }

    @Override
    public List<UrlInfo> getAllUrlShortening() {
        List<UrlShorteningEntity> urlShorteningList = urlShorteningRepository.findAll();
        return urlShorteningList.stream().map(entity -> new UrlInfo(entity.getId(), entity.getAlias(), entity.getOriginUrl(), entity.getCreateDate())).collect(Collectors.toList());
    }
}
