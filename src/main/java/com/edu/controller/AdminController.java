package com.edu.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 Admin(관리자단)을 접근하는 클래스.
 * 변수(Object)를 만들어서 jsp로 전송 + jsp 값을 받아서 Object로 처리
 * @author csy5770
 *
 */
@Controller
public class AdminController {
	//디버그용 로그 객제 생성
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
    //이 메소드는 회원목록을 출력하는 jsp와 매핑이 됩니다.
	@Inject
	   private IF_MemberService memberService;
	   
	@RequestMapping(value="/admin/member/member_list", method=RequestMethod.GET)
		public String selectMember(PageVO pageVO) throws Exception {
	      //jsp 검색시 search_type,search_keyword로 내용이 PageVO클래스에 Set 됩니다.
	      
	      //역방향: 검색한 결과를 jsp로 보내줍니다.
	      if(pageVO.getPage() == null) {
	         pageVO.setPage(1);
	      }
	      //pageVO의 calcPage메서드를 실행하려면, 필수 변수 값 입력(아래)
	      pageVO.setQueryPerPageNum(10);
	      logger.info("디버그"+pageVO.toString());
	      List<MemberVO> listMember = memberService.selectMember(pageVO);
	      pageVO.setTotalCount(listMember.size());//검색이 되든 되지 않든 결과는 전체 카운트 값.
	      return "admin/member/member_list"; //jsp파일 상대경로
	   }
	// @RequestMapping 에서는 반드시 URL을 절대경로로 표기
	@RequestMapping(value="/admin", method =RequestMethod.GET)
	public String admin(Model model) throws Exception{//에러 발생시 Exception을 스프링으로 보내게 됩니다.
		
		//아래 상대경로에서 /WEB-INF/views/폴더가 루트(생략됨 prefix 사용해서)
		//아래 상대경로에서 home.jsp 중 .jsp(생략됨 suffix 사용해서)
		return "admin/home"; //리턴 경로 = 접근경로는 반드시 상대경로 표시
	}
}
