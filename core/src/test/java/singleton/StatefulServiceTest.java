package singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;


class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : 사용자 A 10000원 주문
        // statefulService1.order("userA", 10000);
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : 사용자 B 20000원 주문
        // statefulService2.order("userB", 20000);
        int userBPrice = statefulService2.order("userA", 20000);

        // ThreadA : 사용자 A 주문 금액 조회
        // -> 사용자 A 주문 금액을 조회할 때 사용자 B도 조회를 한다. 위 2개는 결과적으로 같은 인스턴스를 사용해서
        //    나중에 주문한 20000원이 출력된다.
        // int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);

        // 검증
        // Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}