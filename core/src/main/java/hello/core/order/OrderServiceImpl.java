package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // -> 이건 필드 주입 - 이건 의존 관계를 필드에 주입해버린다. 코드가 간결해서 옛날에는 자주 사용했지만 - Anti 패턴이다. -> 이렇게 하면 외부에서 변경이 불가능해서 Test가 힘들다.
    // -> 필드 주입을 하게 되면 Setter를 만들어줘야하고, 스프링 컨테이너를 다 띄워야 한다.
    // @Autowired MemberRepository memberRepository;
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    // 예제 때문에 Setter 주입을 작성함 -> Setter 주입을 사용하면 final을 사용하지 못해 우선 위의 final 삭제하였음
    @Autowired(required = false) // -> @Autowired는 주입할 대상이 없으면 에러가 발생해서 주입할 대상이 없어도 동작하게 하기 위해 false로 설정
    public void setMemberRepository(MemberRepository memberRepository) {
        // setter 주입은 순서를 보장하지 않는다. 다만 생성자 주입은 빈 등록과 동시에 주입되기 때문에 1번째로 출력된 것
        System.out.println("[2번째 호출(setter)] memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("[2번째 호출(setter)] discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }

    // 이렇게 하면 DIP 원칙을 지킴 / 인터페이스에만 의존하고, 구체적인 클래스에 대한 정보를 모른다.
    /**
     * Spring에는 크게 2가지 라이프 사이클이 있다. -> 빈이 등록되는 과정 / 의존 관계가 설정되는 과정
     * [ 중요 ]
     * 생성자 주입은 다른 방법과 좀 다르다. 생성자 주입은 스프링이 객체를 생성할 때 어쩔 수 없이 생성자를 불러야 한다.
     * 그래서 Bean을 등록하면서 의존 관계 주입이 같이 일어난다.
     * 예를 들어 OrderServiceImpl 객체를 스프링이 빈에 등록할려면 Spring도 new OrderServiceImpl(); 처럼 생성해야 한다.
     * @Autowired가 붙어 있으면 스프링 컨테이너에서(예를 들어 하단 메서드를 예시로) MemberRepository, DiscountPolicy 2개를 빈 등록된 것을
     * 가져와서 주입한다. -> 그래서 setter 주입과 비교한다면 생성자 주입이 먼저 호출된다.
     */
    @Autowired //-> 생성자가 1개라면 @Autowired 생략 가능하다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("[1번째 호출] discountPolicy = " + discountPolicy);
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
