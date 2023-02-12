package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // OrderServiceImpl이 자기가 직접 구체적인 정책까지 선택  - 직접 객체를 생성하고, 구체적인 선택까지 자기가 해서 할당한다.
    // -> 역할(인터페이스) / 구현체(클래스)
    private final DiscountPolicy discountPolicy;

    // 이렇게 하면 DIP 원칙을 지킴 / 인터페이스에만 의존하고, 구체적인 클래스에 대한 정보를 모른다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // Test 용도로 작성
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
