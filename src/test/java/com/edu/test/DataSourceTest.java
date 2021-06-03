package com.edu.test;

import java.sql.Connection;
import java.sql.SQLException;

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
