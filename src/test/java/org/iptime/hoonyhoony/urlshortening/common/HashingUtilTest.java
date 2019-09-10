package org.iptime.hoonyhoony.urlshortening.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HashingUtilTest {
    String rightURL = "http://hoonyhoony.iptime.org" + 0;

    /**
     * url 단축 테스트 - 정상 url
     *  - 비정상 Url은 앞서 수행되는 ValidatorUtil 에서 check 되어 url 형식의 String만 전달됨.
     */
    @Test
    public void generateHashingAliasTest() {
        assertEquals(HashingUtil.generateHashingAlias(ValidatorUtil.checkUrl(rightURL)), "AqGEuc");
        assertEquals(HashingUtil.generateHashingAlias(ValidatorUtil.checkUrl("http://naver.com0")), "di9Dmc");
    }

}