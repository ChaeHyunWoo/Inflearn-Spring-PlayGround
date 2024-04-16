package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * @Scope(value = "request") <br>
 * 이렇게 하면 이 Bean은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에서 소멸된다. <br>
 * 정확히는 스프링 컨테이너에 요청할 때 생성되는 것
 *
 * proxyMode = ScopedProxyMode.TARGET_CLASS <br>
 * 적용 대상이 클래스이면 TARGET_CLASS, 인터페이스이면 TARGET_INTERFACE 작성 <br>
 * 이 방법은 CGLIB 라는 라이브러리로 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다. <br>
 * 그리고 스프링 컨테이너에 "myLogger" 라는 이름으로 진짜 대신 이 가짜 프록시 객체를 등록한다. <br>
 * 그래서 의존 관계 주입도 이 가짜 프록시 객체가 주입된다. <br>
 *
 * 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직이 있다.(진짜 myLogger를 찾는 방법을 알고 있다.)
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid +"]" + "[" + requestURL + "] " + message);
    }
    // 초기화 메서드를 사용해서 uuid를 생성 후 저장
    // 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분 가능
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid +"] request scope bean create : " + this);
    }
    // 빈이 소멸되는 시점에서 사용해서 종료 메시지를 남금
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid +"] request scope bean close : " + this);
    }
}
