package kr.or.test;
/**
 * 이 클래스는
 * 1. 이 클래스는 클래스 상속(extends) 연습,
 * 2. 오브젝트(객체) 생성과 new 키워드(예약어) 생성자 메서드
 * 3. 추상클래스(Abstract) 연습
 * @author admin
 *
 */
//오브젝트 생성에 필요한 new 키워드 생성자메서드 사용한 실습(아래)
class Circle {
	private int r;//원의 반지름으로 쓸 변수.
	// 생성자 메서드 (아래)
	// public Circle() { } 만들지 않아도 자동으로 생성 됨.
	public Circle(int radius) {
		r = radius;//Get|Set 중에 Set 저장 멤버변수 처리
	}
	//원을 넓이를 구하는 메서드 반환(리턴) 값의 크기가 더블형 (아래)
	public double getCircle() {
		double result = r*r*3.14;//
		return result;
	}
}
//클래스 상속에 대한 연습(아래)
class Employee { //고용(사원,취업)
	int mSalary; //m? 멤버변수
	String mDept; // 사원 부서 변수
	public void doJob() {
		System.out.println("환영합니다. 직원 여러분!");
	}
}
class Salesman extends Employee {//extends 확장 -> 상속
	public Salesman() { //클래스 명과 똑같은 이름의 메서드가 생성자 함수
		// 자동으로 생성되는데, 개발자가 커스터마이징 할 때 만듦.(아래)
		mDept = "판매 부서";//부모 클래스의 변수를 상속받았기 때문에 사용가능.
	}
	public void doJob() {
		System.out.println("환영합니다. "+mDept+" 여러분!!!");
	}
}
class Development extends Employee {
	public Development() {
		mDept = "개발 부서";
	}
	public void doJob() {
		System.out.println("환영합니다. "+mDept+"여러분!!!!");
	}
}
// 추상 클래스 구현 코딩
abstract class GraphicObject {
	int x,y;
	abstract void draw();// 구현 내용이 없고, 메서드 명만 존재. 추상 메서드 입니다.
	//추상 메서드를 만드는 이유는 메서드가 수십개 수백개일 때 개발자 구현할 때 어려움이 존재.
	//위 문제를 해소한 기능의 클래스 수백개의 메서드를 이름만 모아서, 유지보수가 편리하게 처리한 방식
}
//추상 클래스를 사용하는 방법(아래)
//스프링에서는 추상클래스 보다는
class Triangle extends GraphicObject {
	//삼각형 그리기
	@Override //부모 클래스의 메서드를 재정의, 오버라이드라 명명. 오버로드와 차이점 확인 Step1.java에서 확인.
	void draw() {
		//삼각형을 만듦.
		System.out.println("  * ");
		System.out.println(" * * ");
		System.out.println("*****");	
	}
}
class Rectangle extends GraphicObject {
	//사각형 그리기
	@Override
	void draw() {
		System.out.println("*****");
		System.out.println("*****");
		System.out.println("*****");
		//
	}
}
public class ClassApp {

	public static void main(String[] args) {
		//추상클래스를 이용해서 오버라이드 메서드 사용(아래)
		GraphicObject triangleObject = new Triangle(); //추상클래스는 객체로 사용 불가.
		GraphicObject rectangleObject = new Rectangle();
		triangleObject.draw();
		rectangleObject.draw();
		// 개발자가 입력한 반지름의 원의 넓이를 구하는 오브젝트를 생성(아래)
		Circle circle = new Circle(5); // 반지름이 5인 원의 넓이를 구하는 객체 생성
		System.out.println("원의 넓이는 " + circle.getCircle());
		circle = null; //메모리반환
		
		Employee employee = new Employee();
		Salesman salesman = new Salesman();
		Development development = new Development();
		employee.doJob();
		salesman.doJob();
		development.doJob();
	}

}
