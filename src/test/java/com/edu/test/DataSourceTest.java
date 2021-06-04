package com.edu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
		for(int cnt=0;cnt<100;cnt++) {
			stmt.executeQuery("insert into dept02 values("+cnt+",'디자인부','경기도')");
		}
		//sql 디벨로퍼에서는 커밋이 필수, 외부 java 클래스에서 insert할 때는 자동 커밋이 됨.
		//테이블에 입력되어 있는 레코드를 select 쿼리 stmt 문장으로 가져옴(아래)
		ResultSet rs = stmt.executeQuery("select * from dept02 order by deptno");
		//위에서 저장된 rs 객체를 반복문으로 출력(아래)
		while(rs.next()) {
			//rs객체의 레코드가 없을 때 까지 반복
			logger.debug(rs.getString("deptno")+" "+rs.getString("dname")+
					" "+rs.getString("loc"));
		}
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
