package hello.core.member;

// MemberService 인터페이스의 구현체 MemberServiceImpl 클래스

/*
설계 변경으로 MemberServiceImpl은 MemoryMemberRepository를 의존하지 않는다!
단지 MemberRepository 인터페이스에만 의존한다.
    (1) MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올 지(주입)는 알 수 없다.
        -> ex) 배우가 대본을 받고 남자 배우로 캐스팅 됬는데 여배우가 어떤 배우가 캐스팅 될지 모른다. 누가 오든 공연을 해야 한다.
    (2) MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할 지는 오직 외부(AppConfig)에서 결정된다.
        -> ex) 외부(AppConfig) : 공연 기획자
    (3) MemberServiceImpl 은 이제부터 "의존 관계에 대한 고민은 외부"에 맡기도 오직 "실행에만 집중" 한다.
 */
public class MemberServiceImpl implements MemberService {

    // MemberServiceImpl 클래스에는 이제 추상화에만 의존한다. -> DIP 지킨다. -> MemberRepository에 대한 구체적인 내용은 모름 -> 생성자 DI
    private final MemberRepository memberRepository;

    /*
    (1) appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl을 생성하면서 생성자로 전달한다.
    (2) Client인 "memberServiceImpl" 입장에서 보면 의존 관계를 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection)
        우리 말로 의존성(의존관계) 주입이라 한다.
        의존관계 주입 = 의존성 주입 같은말이다. -> 토비의 스프링에서는 의존관계 주입이라 작성됬지만, 대부분 번역본에는 의존성 주입이라 작성되어 있음
     */

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
