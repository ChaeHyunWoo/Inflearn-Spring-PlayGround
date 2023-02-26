package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
// @Qualifier("mainDiscountPolicy")
@Primary
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    /*
    *   Ctrl + Shift + T -> 메서드 레벨에서 입력 시 Test 코드 생성
    *   -> 10% 할인 로직을 Test 코드로 통해 검증해야 한다. @매우 중요@
     */
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
