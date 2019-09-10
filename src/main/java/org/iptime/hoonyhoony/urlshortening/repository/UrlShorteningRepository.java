package org.iptime.hoonyhoony.urlshortening.repository;


import org.iptime.hoonyhoony.urlshortening.repository.entity.UrlShorteningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShorteningRepository extends JpaRepository<UrlShorteningEntity, Long> {
    Optional<UrlShorteningEntity> findFirstByAlias(String alias);
    Optional<UrlShorteningEntity> findFirstByOriginUrl(String originUrl);
}
