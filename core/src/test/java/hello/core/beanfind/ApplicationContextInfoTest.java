package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String [] beanDefinitionNames = ac.getBeanDefinitionNames();
        // [자동 완성] iter + Tab -> 리스트나 배열이 있을때 쓰면 항상된 for문이 자동으로 완성된다.
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + "object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String [] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionsName : beanDefinitionNames) {
            // BeanDefinition : 빈의 메타데이터 정보
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionsName);

            // ROLE 종류는 3가지인데 보통 ROLE_APPLICATION을 사용함
            // Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
            // Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionsName);
                System.out.println("name = " + beanDefinitionsName + " object = " + bean);
            }
        }
    }
}
