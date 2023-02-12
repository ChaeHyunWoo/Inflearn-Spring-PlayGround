package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링이 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 2. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        /*
        Test 결과
        -> MemberService를 생성 할때마다 다른 객체가 생성되면서 JVM에 올라간다.
        -> 결론은 호출할 때마다 다른 객체가 생성되는 것이다.
        -> 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. -> 싱글톤 패턴(Singleton Pattern)
        memberService1 = hello.core.member.MemberServiceImpl@6f4a47c7
        memberService2 = hello.core.member.MemberServiceImpl@ae13544
         */
        // memberService1, memberService2 와 다른지 검증 !=
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        // 검증
        // same ==
        assertThat(singletonService1).isSameAs(singletonService2);
        /*
        결과
        singletonService1 = singleton.SingletonService@7c9d8e2
        singletonService2 = singleton.SingletonService@7c9d8e2
         */
        /*
        결론 : 스프링 컨테이너를 쓰면 기본적으로 객체를 싱글톤으로 만들어서 관리해준다.
        참고 : 싱글톤 패턴을 구현하는 방법은 여러 가지가 있다. 여기서는 객체를 미리 생성해두는 가장 단순하고 안전한 방법 사용
        -> 싱글톤 패턴을 적용하면 고객의 요청이 올 때마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로
           사용할 수 있지만 싱글톤 패턴은 다음과 같은 수 많은 문제점들을 가지고 있다.

        싱글톤 패턴 문제점
        - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어감.
        - 의존관계상 클라이언트가 구체 클래스에 의존하게 된다. -> DIP 위반
        - 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
        - 테스트하기 어렵다.
        - 내부 속성을 변경하거나 초기화가 어렵다.
        - private 생성자로 자식 클래스를 만들기 어렵다.
        - 결론적으로 유연성이 떨어지고, 안티 패턴으로 불리기도 한다.
        */
    }

    // 싱글톤 컨테이너
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        // 스프링 컨테이너 사용
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        // 참조값이 다른 것을 확인 -> 스프링 컨테이너에서 인스턴스를 가져온다.
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }
    /*
    [결론]
    스프링  컨테이너 덕분에 고객의 요청이 올 때마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 재사용할 수 있다.

    [참고]
    스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것이 아니다.
    요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다. -> 자세한 내용은 [빈 스코프] 파트에서 설명 예정
     */

}
