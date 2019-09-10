package org.iptime.hoonyhoony.urlshortening.common;

import org.iptime.hoonyhoony.urlshortening.exception.CustomException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorUtilTest {
    String rightURL1 = "http://hoonyhoony.iptime.org";
    String rightURL2 = "http://hoonyhoony.iptime.org/";
    String wrongURL = "wrong_no_protocol";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * 정상적인 형식url 체크
     */
    @Test
    public void rightURLCheck1() {
        assertEquals(ValidatorUtil.checkUrl(rightURL1), "http://hoonyhoony.iptime.org");
    }

    /**
     * 정상적인 형식url 체크 (끝에 "/" 있는 url)
     */
    @Test
    public void rightURLCheck2() {
        assertEquals(ValidatorUtil.checkUrl(rightURL2), "http://hoonyhoony.iptime.org");
    }

    /**
     * 잘못된 url 체크
     */
    @Test
    public void wrongURLCheck1(){
        expectedException.expect(CustomException.class);
        expectedException.expectMessage("잘못된 URL 입니다.");
        assertEquals(ValidatorUtil.checkUrl(wrongURL),"wrong_no_protocol");
    }

    /**
     * url 공백 체크
     */
    @Test
    public void wrongURLCheck2() {
        expectedException.expect(CustomException.class);
        expectedException.expectMessage("잘못된 URL 입니다.");
        assertEquals(ValidatorUtil.checkUrl(""), "");
    }

}