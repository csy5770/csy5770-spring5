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
   //컨트롤러 수정하면 자동로딩(Auto컴파일)
   //디버그용 로그객체 생성
   private Logger logger = LoggerFactory.getLogger(AdminController.class);
   //이 메소드는 회원목록을 출력하는 jsp와 매핑이 됩니다.
   @Inject
   private IF_MemberService memberService;
   
   @RequestMapping(value="/admin/member/member_list", method=RequestMethod.GET)
   public String selectMember(PageVO pageVO) throws Exception {
      //jsp 검색시 search_type,search_keyword로 내용이 PageVO클래스에 Set 됩니다.
      
      //역방향: 검색한 결과를 jsp로 보내줍니다.
      if(pageVO.getPage() == null) { //jsp에서 전송값이 없을때만 초기값 강제로 입력.
         pageVO.setPage(1);
      }
      //pageVO의 clacPage메소드를 실행하려면 필수 변수값을 입력해야함(아래)
      pageVO.setQueryPerPageNum(10);
      pageVO.setPerPageNum(10);//하단 UI에 보여줄 페이지 수
      pageVO.setTotalCount(memberService.countMember(pageVO)); //검색되든 안되든 결과의 전체카운트값.
     
     
      List<MemberVO> listMember = memberService.selectMember(pageVO);
      
      logger.info("디버그"+pageVO.toString()); //지금까지 jsp -> 컨트롤러 일방향 자료이동.
      return "admin/member/member_list"; //jsp파일 상대경로
   }
   //URL요청 경로=리퀘스트맵핑 는 반드시 *절대경로*로 표시.
	@RequestMapping(value="/admin", method =RequestMethod.GET)
	public String admin(Model model) throws Exception{//에러 발생시 Exception을 스프링으로 보내게 됩니다.
		
		//아래 상대경로에서 /WEB-INF/views/폴더가 루트(생략됨 prefix 사용해서)
		//아래 상대경로에서 home.jsp 중 .jsp(생략됨 suffix 사용해서)
		return "admin/home"; //리턴 경로 = 접근경로는 반드시 상대경로 표시
	}
}
