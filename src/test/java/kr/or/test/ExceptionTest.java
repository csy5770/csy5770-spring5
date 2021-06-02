package kr.or.test;
/**
 * 이 클래스는 자바에 대해서
 * @author csy5770
 *
 */
public class ExceptionTest {

	public static void main(String[] args) {
		// 외부 클래스 형 변수에 값을 저장해서 출력
		MemberVO memberVO = new MemberVO();
		// 동일 패키지에 포함 된 클래스는 import 없이 사용 가능.
		memberVO.setName("홍길동");
		memberVO.setAge(10);
		memberVO.setPhoneNum("000-0000-0000");
		//System.out.println("한번에 출력하고 싶을때 toString()메서드를 만듭니다.");
		System.out.println(memberVO.toString());
		
		//배열변수 선언
		String[] stringArray = {"10","2a","100"};
		int indexValue = 0;
		for(int cnt=0;cnt<=3;cnt++) {
			/**
			 * try {(필수)} 
			 * catch() {에러 발생시 처리할 구현 내용} 
			 * finally {(선택사항)에러 유무의 상관없이 무조건 실행}
			 */
			try {
				indexValue = Integer.parseInt(stringArray[cnt]);
			} catch(NumberFormatException ex) {//Exception 대신에 선별적으로 예외사항을 캐치합니다.
				
				System.out.println(cnt + "번째 숫자의 형태가 올바르지 않습니다. 확인해 주세요.");
				System.out.println("에러내용은 :" + ex.toString());
			} catch(ArrayIndexOutOfBoundsException ex) {
				System.out.println(ex.toString());
				System.out.println("에러내용은 배열의 크기가 올바르지 않습니다.");
			} finally {
				System.out.println(cnt + "번째 프로그램이 실행 되었습니다.");
			}
			System.out.println("프로그램이 정상종료 되었습니다.");
		}
			
	}

}
