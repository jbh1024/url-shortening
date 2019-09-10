package org.iptime.hoonyhoony.urlshortening.common;

public class HashingUtil {
    private static final String CODEC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * @param originUrl - Hashing 전의 원천 Url.
     * @return - Hashing 된 단축 url의 alias
     */
    public static String generateHashingAlias(String originUrl) {
        return encodeIntToBase62(generateUrlToIntHashCode(originUrl)); //Hashing 후 base62로 인코딩
    }

    /**
     * @param originUrl -- hashcode로 변환할 orl
     * @return -- hashing 된 int 값
     */
    private static int generateUrlToIntHashCode(String originUrl) {
        int hachCode = 0;
        if (originUrl.length() > 0) {
            char value[] = originUrl.toCharArray();
            for (int i = 0; i < originUrl.length(); i++) {
                hachCode = ((hachCode << 5) - hachCode) + value[i]; //시프트 연산으로 변경
            }
        }
        return hachCode & 0x7fffffff; // 음수 처리 후 return;
    }

    /**
     * @param value - Hasing 된 Int 타입 변수 -- Int형
     * @return Base62로 변환된 String ; alias 길이 줄이고 url 뒤에 alias로 사용하기위해 Base62 위해 변환.
     */
    private static String encodeIntToBase62(int value) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = value % 62;
            sb.append(CODEC.charAt(i));
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }


}
