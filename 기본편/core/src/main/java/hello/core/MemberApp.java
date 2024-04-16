package hello.core;


import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();

        // -> Spring으로 전환 : AppConfig에 있는 환경설정 정보를 Spring이 @Bean이 붙은 메서드를 컨테이너에 넣어서 관리해준다.
        // -> ApplicationContext를 스프링 컨테이너라고 한다.
        //      (1) 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다.
        //      (2) 여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록
        //      (3) 스프링 빈은 @Bean이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.
        //      (4) 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // @Bean으로 등록하면 Bean의 이름은 메서드 이름으로 등록된다.
        // -> name:memberService / Type : MemberService.class
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
