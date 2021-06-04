--8장 함수(count,upper,lower,to_char,round...) 그룹 함수
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
