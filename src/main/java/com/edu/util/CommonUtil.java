package com.edu.util;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 이 클래스는 이 프로젝트에서 공통으로 사용하는 유틸리티 기능을 모아놓은 클래스.
 * @author csy5770
 * 컨트롤러 기능 @Controller
 * 컴포넌트 @Component는
 */
@Component
public class CommonUtil {
	//RestAPI서버 연습 ID 중복체크, 실 납품용은 @RestController 사용
	@RequestMapping(value="/id_check", method=RequestMethod.GET)
	@ResponseBody//반환 받은 값의 헤더 값을 제외 후, 내용(body)만 반환함.
	public String id_check() throws Exception {
		
		return null;
	}
}
