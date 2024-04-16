package singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // Impl로 꺼내는 이유는 Test용으로 Impl에만 Test 코드를 작성했기때문
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        // 또 다른 방법으로 검증
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // Test
        // -> 싱글톤이 깨지는지 아닌지 확인
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);

        System.out.println("memberRepository = " + memberRepository);

        /*
        [결과]
        memberService -> memberRepository = hello.core.member.MemoryMemberRepository@7f6f61c8
        orderService -> memberRepository = hello.core.member.MemoryMemberRepository@7f6f61c8

        memberRepository = hello.core.member.MemoryMemberRepository@7e8dcdaa
         */
        // static import : 단축키 : Mac -> Option + Enter / Windows -> Alt + Enter
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    /**
     AppConfig.java에서 각 클래스에 System.out.println("call AppConfig.memberRepository");를 작성했다.
     하지만 예측과 다르게 모두 1번 씩만 호출되었는데 왜 이렇게 되는지 확인을 위해 Test 작성

     AppConfig.java에 @Configuration을 주석 처리 후 Test를 실행하면
     [AppConfig] bean = class hello.core.AppConfig 와 같이
     내가 만든 순수 Java 코드가 출력된다. 하지만 memberRepository가 3번 호출되서 싱글톤이 깨지게 된다.
     이렇게 되면 MemberServiceImpl 에 있는 MemberRepository는 스프링 컨테이너가 관리하는 Bean이 아니다.
    */
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // getClass() : 해당 클래스 타입을 가져온다.
        System.out.println("[AppConfig] bean = " + bean.getClass());
        //[AppConfig] bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$df7dd67e
    }
}
