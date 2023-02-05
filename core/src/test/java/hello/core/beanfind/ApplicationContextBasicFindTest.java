package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("[memberService] = " + memberService);

        // 검증 - static import : mac [ Option + Enter ] / Windows [ Alt + Enter ]
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로 빈 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        System.out.println("[memberService] = " + memberService);

        // 검증 - static import : mac [ Option + Enter ] / Windows [ Alt + Enter ]
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    /*
    *   위의 2가지 방법은 인터페이스로 빈을 조회하는 방법
     */

    // 구현보단 역할에 의존해야 한다. -> 그래서 MemberServiceImpl(구현체)를 적는 것은 좋은 방법은 아니지만 강의에서는 이렇게 진행
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        // 검증 - static import : mac [ Option + Enter ] / Windows [ Alt + Enter ]
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // Error에 대한 테스트 코드
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        // MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
        // -> 예외가 터져야 Test가 성공한다.
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx", MemberService.class));
    }
}
