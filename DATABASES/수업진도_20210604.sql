--19장 사용자 추가시(CreateWorkSpace)시 오라클 데스크탑X, SQLPLUS 사용안함
-- 대신 웹프로그램을 사용(http://127.0.0.1:9000/apex/f?p=4950)
--15장 PK 생성시 자동으로 2가 생성 NOT NULL(빈 값 방지), UNIQUE(중복 방지)
--제약조건(contratint)이 자동생성, Index(테이블)도 자동생성 (검색)
--ERD로 게시판 테이블-[댓글|첨부파일]Foreign KEY(외래키) 부자 관계 형성
--14장 트랜스액션 DB 단에서 사용 X
--스프링 단에서 트랙스액션을 사용 @Transactional 인터페이스를 사용
--commit과 rollback;(DML문:insert,update,delete)
--rollback은 제일 마지막 커밋된 상태로 되돌림.
--12 테이블 구조 생성(create;), 변경(alter;), 삭제(drop;)
--ERD 관계형 다이어그램으로 물리적 테이블 생성
DROP TABLE tbl_member_del;
CREATE TABLE TBL_MEMBER_DEL
(
USER_ID VARCHAR(50) PRIMARY KEY
,USER_PW VARCHAR(255)
,USER_NAME VARCHAR(255)
,EMAIL VARCHAR(255)
,POINT NUMBER(11,0)
,ENABLED NUMBER(1)
,LEVELS VARCHAR(255)
,REG_DATE TIMESTAMP
,UPDATE_DATE TIMESTAMP
);
--ALTER 테이블로 필드명 변경(아래)
ALTER TABLE tbl_member_del RENAME COLUMN email TO user_email;
--DEPT테이블의 dept 숫자 2자리 때문에 에러 -> 4자리로 변경
DESC dept;
ALTER TABLE dept MODIFY(deptno NUMBER(4));
--11 서브쿼리
--단일행서브쿼리 필드 값을 비교할 때, 비교하는 값이 단일
--다중행서브쿼리 테이블 값을 select 쿼리로 생성(레코드 형식)
--10장 테이블 조인 2개의 테이블을 연결(조인)해서 결과를 구함
--댓글 갯수 구할 떄.
--카티시안프러덕트 조인(합집합-게시물10개,댓글100개 조인하면 110개~최대1000개)
--(인너)조인(교집합)을 제일 많이 사용.(인너조인만 실습,INNER JOIN 인너는 생략)
SELECT dept.dname, emp.* FROM emp, dept WHERE emp.deptno = dept.deptno
AND emp.ename = 'SCOTT' ; --오라클방식
SELECT d.dname, e.* FROM emp e JOIN dept d ON e.deptno = d.deptno
WHERE e.ename = 'SCOTT'; --표준쿼리방식
--조인과 그룹을 이용해서 댓글카운터를 출력하는 게시판 리스트 만들기.
SELECT bod.bno,title,count(*) as reply_count
,writer,bod.reg_date,view_count
FROM tbl_board BOD JOIN tbl_reply REP
ON BOD.bno = rep.bno 
GROUP BY bod.bno, title, writer,bod.reg_date,view_count
ORDER BY bod.bno;
--9장은 패스합니다(레포트용 함수)
--8장 함수(count,upper,lower,to_char,round...) 그룹 함수
--having은 group by의 조건문을 적습니다.
--부서별 평균 연봉이 2000 이상인 부서의 번호와 부서별 평균 급여.
SELECT deptno, round(avg(sal)) FROM emp
GROUP BY deptno
HAVING avg(sal) >= 2000;
--부서별 연봉의 합계를 구해서 가장 급여를 많이 받는 부서(아래)
--자바코딩에서는 소문자로 통일(select)
--DB세팅에서 대소문자 구문해서 사용할지, 구분하지 않을 지 세팅
SELECT R.* FROM (
SELECT deptno, SUM(sal) AS dept_sal  
FROM emp GROUP BY deptno
) R ORDER BY dept_sal DESC;--R의 역할은 AS 역할.
SELECT deptno, SUM(sal) FROM emp
GROUP BY deptno
ORDER BY SUM(sal) DESC;
--라운드 함수(반올림) 소수점기준. round(10.05,-2)소수점 2번째에서 반올림
SELECT ename, round(sal,-3) FROM emp;
SELECT SUM(sal) FROM emp;
SELECT round(AVG(sal)) FROM emp;
SELECT COUNT(*) FROM emp WHERE sal >=
(SELECT round(AVG(sal)) FROM emp);
--위 쿼리는 사원 중 평균 연봉 이상인 사원의 수
-- 위 AVG 함수를 where 조건에 사용하지 못할 때 서브쿼리 이용.
SELECT MAX(sal)
, MIN(sal)
, MAX(sal)-MIN(sal) AS "대표와사원의연봉차 " 
FROM emp;
--DDL문(Create; alter;), DCL문(commit; rollback;)
--DML문(Data Manufacture Language) insert,update,delete
--insert문:테이블에 새로운 레코드(row) 추가
CREATE TABLE dept02 AS SELECT * FROM dept WHERE 1=0;
-- 위 쿼리를 실행하면 dept 테이블과 구조와 내용이 동일한 테이블이 생성 됨.
-- where 조건이 붙으면, 구조는 같으나 내용은 빈 테이블이 생김.
INSERT INTO dept02 (
--필드명
deptno, dname, loc
) VALUES(
--바인딩값
10, '개발부서', '천안'
);
insert into dept02 values(20,'디자인부','경기도');
--DCL 명령어에 속하는 커밋이 필수.
COMMIT;--데이터베이스쿼리로 직접 입력한 결과는 커밋을 해야 저장이 됨.
-- 커밋을 하지 않으면, 쿼리에서만 출력이 됨.
SELECT * FROM dept02 ORDER BY deptno;
--DELETE 는 레코드 1행을 지우는 명령
DELETE FROM dept02; -- 이렇게 사용하면 안됨.
DELETE FROM dept02 WHERE deptno >= 0;--모두 삭제 where 반드시 포함.
--DROP table 테이블명 은 테이블자체를 물리적으로 없애는 명령
DROP TABLE dept02; --드롭 테이블은 커밋 없이 바로 적용 됨.
CREATE TABLE emp01 AS SELECT * FROM emp; -- 테이블 복사 명령
SELECT * FROM emp01;
--UPDATE 테이블명 SET 필드명 = '변경할 값' where empno='특정 ID'
UPDATE emp01 SET ename = '최서영' where empno = 7839;
ROLLBACK; -- 롤백은 마지막 커밋 바로 전까지 되돌림.
UPDATE emp01 SET sal = sal * 1.1; --모든 직원의 연봉을 10% 인상.
UPDATE emp01 SET hiredate = sysdate;
