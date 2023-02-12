package hello.core.scan.filter;

import java.lang.annotation.*;

/**
 * 내가 만든 어노테이션
 * @MyIncludeComponent 가 붙어있으면 컴포넌트 스캔 대상에서 제외한다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
