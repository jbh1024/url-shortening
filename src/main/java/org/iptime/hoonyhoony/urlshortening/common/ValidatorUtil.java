package org.iptime.hoonyhoony.urlshortening.common;

import org.iptime.hoonyhoony.urlshortening.exception.CustomException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static org.iptime.hoonyhoony.urlshortening.exception.ErrorCode.URL_ERROR;

/**
 * Url 형식 체크, Suffix 체크 등 Validation Check 용
 */
public class ValidatorUtil {
    /**
     *  url 체크, 형식 안맞을때 exception 발생시킴
     * @param url -- 단축할 대상 url
     * @return  -- Validation 체크 완료 후 url
     */
    public static String checkUrl(String url) {
        //url 체크 정규식
        String regex = "^(https?):\\/\\/([a-z0-9-]+\\.)+[a-z0-9]{2,4}.*$";
        Pattern p = Pattern.compile(regex);

        if(StringUtils.isEmpty(url) || !p.matcher(url).matches()){
            throw new CustomException(URL_ERROR);
        }

        if(url.endsWith("/")){
            url = url.substring(0, url.length()-1);
        }
        return url;
    }
}
