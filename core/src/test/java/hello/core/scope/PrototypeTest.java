package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // Prototype을 생성하기 전에 출력
        System.out.println("find prototypeBean1");
        // Prototype 은 싱글톤과 다르게 이때 새로운 객체가 생성되면서 init()이 호출된다.
        // 프로토타입 스코프의 빈은 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 + " + prototypeBean2);
        // 프로토타입 빈을 2번 조회했으므로 완전히 다른 스프링 빈이 생성되고, 초기화도 2번 실행된다.
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 만약 프로토타입 스코프 빈은 닫아야하면 수동으로 해줘야 함
        // prototypeBean1.destroy();
        // prototypeBean2.destroy();

        ac.close();
        /*
        [ 출력 결과 ]
        find prototypeBean1
        PrototypeBean.init
        find prototypeBean2
        PrototypeBean.init
        prototypeBean1 = hello.core.scope.PrototypeTest$PrototypeBean@15713d56
        prototypeBean2 + hello.core.scope.PrototypeTest$PrototypeBean@63f259c3
         */
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        /**
         * 프로토 타입은 destroy()가 호출되지 않는다.
         *
         */
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean,destroy");
        }
    }
}
