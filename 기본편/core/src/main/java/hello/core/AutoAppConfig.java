package hello.core;

// 컴포넌트 스캔 섹션에서 만듬

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 *  @Component가 붙은 클래스를 찾아서 Bean으로 등록해주는 어노테이션
 *  excludeFilters를 쓴 이유는 이전에 만든 AppConfig.java가 자동으로 빈 등록되는 것을 막기 위해 설정함
 *  -> 쉽게 말하면 ComponentScan을 할건데 @Configuration이 붙은 클래스를 뺄 것이다. (필터 느낌)
 *  -> 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 유지하기 위해 사용한 것
 */

// 추가 설명하자면 @ComponentScan 의 범위를 따로 지정하지 않으면 해당 어노테이션이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
// 권장하는 방법 : 개인적으로 즐겨 사용하는 방법은 패키지 위치를 지정하지 않고, 설정 정ㄹ 클래스의 위치를 프로젝트 최상단에 두는 것이다. 최근에 스프링 부트도 이 방법을 기본으로 제공한다.
@Configuration
@ComponentScan(
        // basePackages = "hello.core.member",
        // basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */

}
