package com.edu.controller;


import org.springframework.stereotype.Controller;

/**
 * 이 클래스는 MVC웹 프로젝트(스프링)을 최초로 생성시 자동으로 생성되는 클래스.
 * 웹브라우저의 요청사항을 view(jsp)에 연결시켜주는 클래스가 @Controller
 * 스프링에서 관리하는 클래스를 스프링빈(콩) = 스프링빈을 명시 @Controller 애노테이션
 * Beans(콩들) 그래프로 이 프로젝트의 규모 확인 가능
 * 스프링이 관리하는 클래스는 파일아이콘에 S가 붙는다
 */
@Controller
public class HomeController {
	//스프링빈(클래스) 에서는 로거로 디버그를 함 = 로거 객체 생성
	//로그중 slf4j(Spring Log For Java)
	//private Logger logger = Logger.
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * return 값으로 view(jsp)를 선택해서 화면에 결과를 표시(렌더링) 합니다.
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";//확장자가 생략 (.jsp)가 생략되어 있음.
	}
	
}
