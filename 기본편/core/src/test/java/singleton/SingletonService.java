package singleton;

public class SingletonService {

    // 이 방식으로 인스턴스를 생성하면 static으로 인해 Class 레벨에 올라가서 딱 1개만 인스턴스를 생성하게 된다.
    // 이렇게 되면 JVM이 구동될 때 바로 static 객체를 생성해서 instance에 넣어둔다.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }
    // private으로 만들면 외부에서 생성할 수 없다. -> Test 용으로 PrivateSingleton 클래스 생성함
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출 성공!");
    }
    /*
    이렇게 하면 완벽한 싱글톤이 된다.
    JVM(자바)가 뜨면서 static 영역에 객체 instance를 미리 생성해서 올려두고, instance의 참조를 꺼낼 수 있는 것은
    오로지 getInstance() 즉, getter 밖에 없다. -> 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 해서 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.
     */
}
