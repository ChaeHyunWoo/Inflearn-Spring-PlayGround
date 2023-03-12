package hello.core.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + "message = " + message);
    }

    // 서비스 종료 시 호출 - 연결 끊기
    public void disconnect() {
        System.out.println("close " + url);
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();;
        call("초기화 연결 메시지");
    }
    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }



    // 과거 방법 - 인터페이스 InitializingBean, DisposableBean 사용
    /**
     *  [ 초기화, 소멸 인터페이스 단점 ]
     *  - 스프링 전용 인터페이스라서 해당 코드가 스프링 전용 인터페이스에 의존한다
     *  - 초기화 소멸 메서드의 이름 변경 불가능
     *  - 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
     *
     *  [ 추가 ]
     *  - 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법 <br>
     *  - 지금은 더 좋은 방법들이 있어서 지금은 거의 사용하지 않는다.
     */

    /**
     * InitializingBean 인터페이스 상속 <br>
     * - afterPropertiesSet() 메서드로 초기화 지원
     */
    /*
    @Override
    public void afterPropertiesSet() throws Exception {
        // 의존 관계가 끝나면 호출
        connect();;
        call("초기화 연결 메시지");
    }
    */

    /**
     * DisposableBean 인터페이스 상속 <br>
     * - destroy() 메서드로 소멸 지원
     */
    /*
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
    */
}
