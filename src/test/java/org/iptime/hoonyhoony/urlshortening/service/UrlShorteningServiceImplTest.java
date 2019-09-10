package org.iptime.hoonyhoony.urlshortening.service;

import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;
import org.iptime.hoonyhoony.urlshortening.repository.UrlShorteningRepository;
import org.iptime.hoonyhoony.urlshortening.repository.entity.UrlShorteningEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UrlShorteningServiceImplTest {

    @Mock
    UrlShorteningRepository urlShorteningRepository;

    @InjectMocks
    UrlShorteningServiceImpl urlShorteningService;

    @Test
    public void generateShorteningUrl_ServerHasNoShorteningUrlTest() {
        //기존에 생성된 단축URL 존재하지 않을 때
        when(urlShorteningRepository.findFirstByOriginUrl(anyString())).thenReturn(Optional.empty());
        assertEquals(urlShorteningService.generateShorteningUrl("http://hoonyhoony.iptime.org"), "AqGEuc");
    }

    @Test
    public void generateShorteningUrl_ServerHasShorteningUrlTest() {
        //기존에 생성된 단축URL 존재 할 때
        UrlShorteningEntity mockUrlShorteningEntity = new UrlShorteningEntity();
        mockUrlShorteningEntity.setOriginUrl("http://hoonyhoony.iptime.org");
        mockUrlShorteningEntity.setAlias("ABCD");
        when(urlShorteningRepository.findFirstByOriginUrl(anyString())).thenReturn(Optional.of(mockUrlShorteningEntity));
        assertNotEquals(urlShorteningService.generateShorteningUrl("http://hoonyhoony.iptime.org"), "AqGEuc");
    }

    @Test
    public void getOriginUrlByAliasTest_ServerHasNoShorteningUrlTest() {
        //기존에 생성된 단축URL 존재하지 않을 때
        when(urlShorteningRepository.findFirstByAlias(anyString())).thenReturn(Optional.empty());
        assertEquals(urlShorteningService.getOriginUrlByAlias("eRmhQ"), null);
    }

    @Test
    public void getOriginUrlByAliasTest_ServerHasShorteningUrlTest() {
        //기존에 생성된 단축URL 존재 할 때
        UrlShorteningEntity mockUrlShorteningEntity = new UrlShorteningEntity();
        mockUrlShorteningEntity.setOriginUrl("https://abcportalapp.azurewebsites.net");
        mockUrlShorteningEntity.setAlias("eRmhQ");
        when(urlShorteningRepository.findFirstByAlias(anyString())).thenReturn(Optional.of(mockUrlShorteningEntity));
        assertEquals(urlShorteningService.getOriginUrlByAlias("eRmhQ"), "https://abcportalapp.azurewebsites.net");
    }

    @Test
    public void getAllUrlShorteningTest() {
        UrlShorteningEntity urlShorteningEntity = new UrlShorteningEntity();
        urlShorteningEntity.setId(8);
        urlShorteningEntity.setAlias("http://localhost:8765/eRmhQ");
        urlShorteningEntity.setOriginUrl("https://abcportalapp.azurewebsites.net");

        List<UrlShorteningEntity> urlShorteningEntityList = new ArrayList<>();
        urlShorteningEntityList.add(urlShorteningEntity);
        when(urlShorteningRepository.findAll()).thenReturn(urlShorteningEntityList);

        List<UrlInfo> urlInfoList = urlShorteningService.getAllUrlShortening();
        assertNotNull(urlInfoList);
        assertTrue(urlInfoList.size() == 1);
    }

}