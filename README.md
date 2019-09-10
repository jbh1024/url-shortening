# <h1>프로젝트 소개 - 단축 Url 생성 (Url Shortenging Project) </h1>
* 이 프로젝트는 Spring Boot(2.1.8.RELEASE) Project 로 <br/>
    java(jdk1.8) 와 jQuery(2.1.3), Bootstrab(3.3.6), Thymeleaf 이용 하여 작성되었습니다. 
* URL 을 입력받아 짧게 줄여주고, Shortening 된 URL 을 입력하면 원래 URL 로 리다이렉트하는 URL Shortening Service입니다.
    * ex) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro
* 아래의 요구사항에 대하여 고려하여 작성하였으며 요구사항에 명시되지 않은 사항은 모두 지원하지 않는 전제 하에 작성되었습니다.
    * ex) '등록 된 수정, 삭제' / '사용자정의 단축url(alias) 등록' 과 같은 부가적인 기능은 <br />
     지원하지 않는 것을 전제로 개발을 진행하였습니다. <br /> (추가적인 개선의 여지는 있습니다.) 
* 요구사항 해결 방법은 아래에 요구사항 및 해결 전략에 기술하였습니다.
<hr/>

##요구사항 및 해결 전략
+ __webapp 으로 개발하고 URL 입력폼 제공 및 결과 출력__
    * 화면은 입력과 결과를 제공하는 메인화면과 전체 리스트를 제공하는 화면, 에러화면으로 구성되어있습니다.
+ __URL Shortening Key 는 8 Character 이내로 생성되어야 합니다.__
+ __Shortening 된 URL 을 요청받으면 원래 URL 로 리다이렉트 합니다.__
+ __Shortening Key 생성 알고리즘은 직접 구현해야 합니다. (라이브러리 사용 불가)__
+ __동일한 URL 에 대한 요청은 동일한 Shortening Key 로 응답해야 합니다.__
    * 위 네가지 사항을 만족시키기 위해 String의 Hashcode 메소드 내용을 활용하여 
      넘겨받은 Url을 hashing 하고 Base62로 인코딩 하였습니다.
      * Hash를 사용한 주요 이유는 아래와 같습니다. 
          > - 동일한 문자열에 대해 동일한 Hashcode값을 return.
          > - return 되는 값이 int형으로 음수를 제외하고 2^31개 결과 생성 가능.<br/>
          (음수는 Base62 "& 0x7fffffff" 연산 후 return 합니다.)
          
    * 입력받은 url String 을 char배열로 변환 후 첫번째 문자부터 마지막 문자까지<br/>
    해시코드 값에 31을 곱하고 해당 문자의 코드값을 더하는 식으로 hashcode를 생성하였습니다.
    * 중복을 피하기 위해 Hashcode를 생성 하기전 재시도 count(총 5회까지 재시도)를 url의 끝에 붙여서 문자열을 생성합니다.   
    * Hashing 과정에서 31을 곱하는 대신 shift연산으로 작으나마 효율을 개선했습니다.
        * Hash과정에서 31을 곱한 이유는 아래와 같습니다.
            > - 일반적으로 소수이면서 홀수를 많이 사용하기 때문입니다. 31 은 소수와 홀수를 모두 만족하는 수.
            > - 홀수를 선택하는 이유는 짝수였고 곱셈 결과가 오버플로되었다면 정보는 사라지기 때문입니다.<br/>
               (2로 곱하는 것은 비트를 왼쪽으로 shift하는 것과 같기 때문.)
            > - 소수를 사용하는 이점은 그다지 분명하지 않지만 전통적으로 널리 사용되기 때문으로 파악하였습니다. 
    * 숫자로 구성된 hashcode는 요구사항에 명시된 길이(8자리) 이내의 길이로 변경하기위해 웹 url에서 사용하기 적합한
        Base62 문자열로 인코딩합니다. 
    * 이때 생성되는 문자열이 기존에 생성되어 있거나(DB상 존재) 길이가 8자리 이상인 경우 5회에 한해 재 생성합니다.
      
    * 프로토콜이 다른경우 다른 Url로 처리하며 url 끝에 "/"(suffix) 문자만 차이날 경우 같은 url로 처리합니다.
        > - ex) http://www.kakaopay.com ≠ https://www.kakaopay.com 
        > - ex) https://www.kakaopay.com = https://www.kakaopay.com/ 
    * 최초 요청시 입력한 url의 validation(url 형식 체크) 을 체크 후 DB에서 요청한 url과 동일 한 url로 생성된 Shortening Url 이 있는지 확인 합니다.
    * 존재할 경우 기존 Shortening Url로, 존재하지 않을 경우 신규로 shortening url 을 생성하여 DB에 저장 후 Shortening Url 로 응답합니다.
    * 생성된 shortening url 을 입력할 경우 등록시 입력된 url 로 리다이렉트 되며, 등록 되지 않은 shortening url 의 경우 error 페이지로 리다이렉트 됩니다.
    * 오류 페이지에서 메인화면으로 이동하여 등록이 가능합니다.  
+ __Database 사용은 필수 아님 (선택)__
    * ~~개인적으로 운영하고 있는 서버에 Maria db를 세팅하였습니다.~~
      >- ~~접속정보는 제거하였습니다.~~ 
    * DB 서버 작업으로인해 H2 디비로 설정을 변경해두었습니다.
    * Shortening Url 서비스 용 database에 Table을 생성하여 생성한 Shortening Url 과 original Url을 관리하고 있습니다.
    * JPA를 통해 데이터를 조회, 저장합니다. 
    
<hr/>

## 빌드 및 실행방법
* 빌드에 특이사항은 없으며 일반적인 Spring Boot 프로젝트 빌드와 실행을 통해 확인합니다.
* http://localhost:9632 에 접속 하여 프로그램을 확인 할 수 있습니다.

