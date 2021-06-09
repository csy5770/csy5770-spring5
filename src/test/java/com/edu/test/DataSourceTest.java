package com.edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.edu.service.IF_MemberService;
import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 클래스는 오라클과 연동해서 CRUD를 테스트하는 클래스 입니다.
 *
 * @author csy5770
 *
 */
//RunWith인터페이스, 현재클래스가 Junit 실행클라스라고 명시
@RunWith(SpringJUnit4ClassRunner.class)
//경로에서 **(모든폴더라는 뜻) , *(모든파일명을 명시)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class DataSourceTest {
	//디버그용 로그 객체변수 생성
	private Logger logger = Logger.getLogger(DataSourceTest.class);
	//dataSource 객체는 데이터베이스 객체를 pool로 저장해서 사용할 때 DataSource 클래스를 사용(아래)
	@Inject //인젝트는 스프링에서 객체를 만드는 방법, 이전 자바에서는 new 키워드로 객체를 만들었음.
	DataSource dataSource; //Inject로 객체를 만들면 메모리 관리를 스프링이 대신해 줌.
	// 자바8부터 Inject 지원. 이전 버전 자바에서는 @Autowired 로 객체를 생성.
	@Inject //MemberService를 주입받아서 객체를 사용(아래)
	private IF_MemberService memberService;
	//스프링 코딩 시작 순서
	//M-V-C 사이에 데이터를 임시 저장하는 공간(VO 클래스-멤버변호+Get/Set메서드) 생성
	//보통 ValueObject 클래스는 DB 테이블과 1:1로 매칭이 됩니다.
	//그래서, MemberVO.java VO클래스를 생성.(필수)
	//2.DB(마이바티스)쿼리를 만듭니다.(VO 사용됨) - 내일부터 시작
	@Test
	public void selectMember() throws Exception {
		//회원관리 테이블에서 더미로 입력한 100개의 레코드를 출력하는 메서드 테스트 -> 회원관리 목록출력
		//현재 100명 검색가능, 페이징 기능 여기서 구현 1페이지에 10명씩 나오게 변경
		//현재 몇페이지, 검색어 임시저장 공간 -> DB에 페이징 조건문, 검색조건문
		//변수를 2-3 이상은 바로 String 변수로 처리하지 않고, VO 만들어 사용
		//PageVO.java 클래스를 만들어서 페이징 처리 변수와 검색어 변수 선언, Get/Set 생성
		//PageVO만들기 전 SQL 쿼리로 가상으로 페이지를 구현 및 필요한 변수 생성
		PageVO pageVO = new PageVO();
	    
	    pageVO.setPage(1);//기본 값으로 1페이지 적용.
	    pageVO.setPerPageNum(10);//UI 하단 페이지 개수
	    pageVO.setQueryPerPageNum(10);//쿼리에서 페이지 당 개수, 쿼리용.
	    pageVO.setTotalCount(memberService.countMember()); //테스트용. 100명 입력.
	    pageVO.setSearch_keyword("admin");
	    //위 위치가 다른 설정보다 상단이면 에러, calcPage()가 실행될 때 위 3가지 변수 값이 저장 되어 있어야 계산 메서드가 작동되기 때문.
	    //위 토탈카운트 변수는 startPage, endPage 계산에 필수
	    //매퍼쿼리_DAO클래스_Service클래스_JUnit(나중에 컨트롤러에서 작업)
	    //pageVO 저장 값 확인 (아래)
	    logger.info("pageVO 저장된 값 확인:"+pageVO.toString());
		List<MemberVO> listMember = memberService.selectMember(pageVO);
		listMember.toString();
	}
	
	@Test
	public void oldQueryTest() throws Exception {
		//스프링빈을 사용하지 않을 때 예전 방식 : 코딩테스트에서는 스프링 설정을 안쓰고, 직접 DB 아이디/암호 입력
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","XE","apmsetup");
		logger.debug("데이터 베이스 직접 접속 성공. DB종류는 " + connection.getMetaData().getDatabaseProductName());
		//직접쿼리를 날립니다. 날리기 전 쿼리문자 객체생성 statements
		Statement stmt = connection.createStatement();
		//위 쿼리문장 객체를 만드는 이유? 보안(SQL인젝션 공격)
		//stmt객체가 없으면, 개발자가 SQL인젝션 방지코딩을 넣어야 합니다.
		//Insert 쿼리 문장을 만듬(아래)
		//예전 방식으로 더미데이터(샘플데이터) 100개를 입력합니다.
		/*
		 * for(int cnt=0;cnt<100;cnt++) {
		 * stmt.executeQuery("insert into dept02 values("+cnt+",'디자인부','경기도')"); }
		 */
		//sql 디벨로퍼에서는 커밋이 필수, 외부 java 클래스에서 insert할 때는 자동 커밋이 됨.
		//테이블에 입력되어 있는 레코드를 select 쿼리 stmt 문장으로 가져옴(아래)
		ResultSet rs = stmt.executeQuery("select * from dept order by deptno");
		//위에서 저장된 rs 객체를 반복문으로 출력(아래)
		while(rs.next()) {
			//rs객체의 레코드가 없을 때 까지 반복
			logger.debug(rs.getString("deptno")+" "+rs.getString("dname")+
					" "+rs.getString("loc"));
		}
		stmt = null;//메모리 반환
		rs = null;//메모리 반환
		connection = null; //메모리 초기화
	}
	
	@Test
	public void dbConnectionTest() {
		//데이터베이스 커넥션 테스트용: 설정은 root-context의 빈(스프링클래스)를 이용
		try {
			Connection connection = dataSource.getConnection();
			logger.debug("데이터 베이스 접속 성공. DB종류는" + connection.getMetaData().getDatabaseProductName());
		} catch (SQLException e) {
			logger.debug("데이터 베이스 접속 실패.");
			//e.printStackTrace();
		}
		
	}
	@Test
	public void junitTest() {
		//로거는 장점>조건에 따라서 출력을 조정할 수 있음.
		logger.debug("Junit테스트 시작 입니다.");
	}
}
