package hello.core.singleton;

// 싱글톤 방식의 주의점
// -> Class 레벨에서 Test 코드 만드는 단축키 : Mac(Command + Shift + T) / Windows(Ctrl+Shift+T)
public class StatefulService {

    // private int price; // 상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        // this.price = price; // -> 여기가 문제!
        return price;
    }

    /*public int getPrice() {
        return price;
    }*/
}
