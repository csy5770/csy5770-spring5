package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 이 클래스는 Admin(관리자단)을 접근하는 클래스.
 * 변수(Object)를 만들어서 jsp로 전송 + jsp 값을 받아서 Object로 처리
 * @author csy5770
 *
 */
@Controller
public class AdminController {
	
	@RequestMapping(value="/admin", method =RequestMethod.GET)
	public String admin(Model model) throws Exception{//에러 발생시 Exception을 스프링으로 보내게 됩니다.
		
		//아래 상대경로에서 /WEB-INF/views/폴더가 루트(생략됨 prefix 사용해서)
		//아래 상대경로에서 home.jsp 중 .jsp(생략됨 suffix 사용해서)
		return "admin/home"; //리턴 경로 = 접근경로는 반드시 상대경로 표시
	}
}
