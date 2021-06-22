package com.edu.util;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;

/**
 * 이 클래스는 이 프로젝트에서 공통으로 사용하는 유틸리티기능을 모아놓은 클래스. 
 * @author csy5770
 * 컨트롤러 기능 @Controller(jsp와 바인딩이 필요할때는 필수 애노테이션 입니다.)
 * 콤포턴트 @Component는 MVC가 아닌 기능들을 모아놓은 스프링빈 명시, 여기서는 jsp와 바인딩이 필요해서 사용않함 
 */
@Controller
public class CommonUtil {
	//멤버변수생성(아래)
	private Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	@Inject
	private IF_MemberService memberService;//스프링빈을 주입받아서(DI) 객체준비
	
	//첨부파일 업로드/다운로드/삭제/인서트/수정에 모두 사용될 저장경로를 1개지정해서 [전역]으로사용
	@Resource(name="uploadPath")
	private String uploadPath;//root-context 업로드경로 클래스빈의 id값을 받아서 String변수 입력
	public String getUploadPath() {
		return uploadPath;
	}

	//첨부파일이 이미지인지 아닌지 확인하는 데이터생성
	private ArrayList<String> checkImgArray = new ArrayList<String>() {
		{
		add("gif");
		add("jpg");
		add("jpeg");
		add("png");
		add("bmp");
		}
	};
	public ArrayList<String> getCheckImgArray() {
		return checkImgArray;
	}
	
	//RestAPI서버 맛보기ID중복체크(제대로 만들면 @RestController 사용)
	@RequestMapping(value="/id_check", method=RequestMethod.GET)
	@ResponseBody //반환받은 값의 헤더값을 제외하고, 내용(body)만 반환하겠다는 명시
	public String id_check(@RequestParam("user_id")String user_id) throws Exception {
		//중복아이디를 체크로지(아래)
		String memberCnt = "1";//중복ID가 있을때, 기본값 1
		if(!user_id.isEmpty()) {//!주의 user_id가 공백이 아니라면,
			MemberVO memberVO = memberService.readMember(user_id);
			logger.info("디버그: " + memberVO);//user_id를 공백을 전송해도 null이기때문에 조건 추가필요
			if(memberVO == null) {//중복아이디가 존재하지 않으면 {}안을 실행
				memberCnt = "0";
			}
		}
		return memberCnt;//0.jsp 이렇게 작동하지 않습니다. 이유는 @ResponseBody때문이고, RestAPI는 값만 반환
	}
}