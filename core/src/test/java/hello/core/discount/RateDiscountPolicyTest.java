package hello.core.discount;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
    }

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_0() {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }


    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_() {
        //given
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
        
        /*
        * Test 코드 작성 시 [Assertions]는 static import  하는 것이 좋다.
        *  단축키 : Mac -> Option + Enter / Windows -> Alt + Enter
        *  --> 단축키 실행 후 : Add on-demand static import 클릭하기
         */
    }

}