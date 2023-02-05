package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
*       이 Application에 대한 환경 설정을 여기서 다 함 (ex:공연 기획자)
*
 *   -> AppConfig는 Application의 실제 동작에 필요한 구현 객체를 생성 한다.
 *          1. MemberServiceImpl
 *          2. MemoryMemberRepository
 *          3. OrderServiceImpl
 *          4. FixDiscountPolicy
 *   -> AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결) 해준다. - Dependency Injection(의존성 주입)
 *          1. MemberServiceImpl -> MemoryMemberRepository
 *          2. OrderServiceImpl -> MemoryMemberRepository, FixDiscountPolicy
 */

// 사용 영역, 구성 영역으로 분리되었는데, AppConfig(구성 영역)은 공연 기획자로 생각하면 공연 참여자인 구현 객체들을 모두 알아야 한다.
/*
public class AppConfig {

    public MemberService memberService() {
        // AppConfig 리팩토링 -> MemoryMemberRepository 역할이 자세히 안보여서
        // -> 단축키 Command + Option + M / Ctrl + Alt + M
        return new MemberServiceImpl(memberRepository());
        // 호출 시 MemberServiceImpl 이라는 객체를 만들고, 애는 MemoryMemberRepository를 사용할 것이다 - DI
    }

    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // orderService() 메서드를 호출하면 OrderServiceImpl를 반환하는데 이 안에는 2개의 객체가 생성되어 있음
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
 */
// -> 위는 순수 Java로 만들었다면 이제부터(하단) Spring 기반으로 AppConfig를 작성할 것이다.
@Configuration
public class AppConfig {

    // @Bean을 붙이면 스프링 컨테이너에 등록된다.
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}