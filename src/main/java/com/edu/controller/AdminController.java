package com.edu.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이클래스는 admin관리자단을 접근하는 클래스입니다.
 * 변수 Object를 만들어서 jsp로 전송 + jsp에서 폼값을 받아서 Object로 처리
 * @author 장효원
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
   public String selectMember(@ModelAttribute("pageVO")PageVO pageVO, Model model) throws Exception {
      /*
        이 메소드는 2개의 객체를 생성해서 jsp로 보내줘야합니다.
       1객체: memberList를 생성, model 통해서 jsp로 전송
       2객체: pageVO객체(prev,next,startPage,endPage)를 생성, model 통해서 jsp로 전송
       2번객체부터 로직이 필요: 2번객체에서 memberList를 구하는 쿼리변수가 만들어지기 때문에.
       */
      if(pageVO.getPage() == null) { //jsp에서 전송값이 없을때만 초기값 강제로 입력.
         pageVO.setPage(1);
      }
      //pageVO의 clacPage()로직 < 변수(객체)값의 이동 확인
      pageVO.setQueryPerPageNum(5); //memberList쿼리에 필요.
      pageVO.setPerPageNum(5); //startPage구할때
      //위 두 변수가 있어야 totalCount를 구할 수 있음.
      //위 두 변수값을 이용해서 아래 setTotalCount메소드에서 calsPage() 호출.
      pageVO.setTotalCount(memberService.countMember(pageVO)); //검색되든 안되든 결과의 전체카운트값.
      //calcPage() 실행되면 prev,next 변수값이 입력됩니다.
      List<MemberVO> listMember = memberService.selectMember(pageVO);
      //위 setPerPageNum이 20이면 next가 false, 5면 true.
      //100명의 회원에서는 하단 페이지번호가 10까지 일 때 nest가 false인게 정상.
      logger.info("디버그"+pageVO.toString()); //지금까지 jsp -> 컨트롤러 일방향 자료이동.
      model.addAttribute("listMember",listMember);
      //model.addAttribute("pageVO", pageVO);
      return "admin/member/member_list"; //jsp파일 상대경로
   }
   //URL요청 경로=리퀘스트맵핑 는 반드시 *절대경로*로 표시.
   @RequestMapping(value="/admin", method = RequestMethod.GET)
   public String admin(Model model) throws Exception { //에러 발생시 Exception클래스의 정보를 스프링으로 보내게 됩니다.
      
      //아래 상대경로에서 /WEB-INF/views/폴더가 루트(최상위)입니다. prefix(접두어)처리 되어있어서 생략가능.
      //아래 상대경로 home.jsp에서 .jsp는 suffix(접미어)처리되어있어서 생략가능. 
      return "admin/home"; //return 경로=접근경로 는 반드시 *상대경로*로 표시.
   }
}