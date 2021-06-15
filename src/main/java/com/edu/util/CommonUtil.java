package com.edu.util;

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
 * 이 클래스는 이 프로젝트에서 공통으로 사용하는 유틸리티 기능을 모아놓은 클래스.
 * @author csy5770
 * 컨트롤러 기능에는 @Controller (jsp와 바인딩)
 * MVC가 아닌 기능들을 모아놓은 스프링빈 메소드 @Component, jsp와 바인딩이 필요해서 사용 안함.
 */
@Controller
public class CommonUtil {
   //멤버변수 생성(아래)
   private Logger logger = LoggerFactory.getLogger(CommonUtil.class);
   @Inject
   private IF_MemberService memberService; //스프링빈을 주입받아서(DI) 객체 준비
   
   //ID중복체크를 RestAPI서버 맛보기로.(제대로 만들려면 @RestController로 만들어야함./댓글)
   @RequestMapping(value="/id_check", method=RequestMethod.GET)
   @ResponseBody //반환받은 값의 헤더값을 제외하고 내용(body)만 반환하겠다는 명시.
   public String id_check(@RequestParam("user_id")String user_id) throws Exception {
      //중복아이디 체크로직(아래)
      String memberCnt = "2"; //아이디 입력이 공백일 때 기본값 2.
      if(!user_id.isEmpty()) { //user_id가 공백이 아니라면 아래 실행.
         MemberVO memberVO = memberService.readMember(user_id);
         logger.info("디버그: " + memberVO); //공백을 전송해도 null이라 사용가능한 아이디로 판정되기 때문에 조건을 하나 더 추가해야한다.
         memberCnt = "1"; //아이디에 공백이 없지만 이미 있는 아이디일때 1.
         if(memberVO == null) { //중복아이디가 존재하는지 확인.
            //중복아이디가 존재하지 않으면 아래 실행
            memberCnt = "0";
         }
      }
      return memberCnt; //@ResponseBody때문에 0.jsp 이렇게 매칭되지 않음.(RestAPI는 값만 반환.)
   }
}