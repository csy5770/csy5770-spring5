--DESC: Description 테이블 구조를 설명
DESC dept;

--SELECT: 테이블내용조회, where 조회조건, as(Alias)별칭: 필드명이 길 때 사용.
--concat: 오라클 내장 함수. 주로 레포트 작성시 많이 씀.
SELECT concat(deptno,'번') as "부서번호"
, dname as "부서명" 
, concat(loc, '시') as "위치"
FROM dept 
WHERE loc = 'NEW YORK';
--DUAL 가상테이블 이름. 테이블이 없는 내용을 select 할 때 사용.
SELECT concat('최','서영') FROM dual;
SELECT 3+5 as "3 더하기 8은" FROM dual;
--WHERE select문 마지막에 쓸 수 있음. 레코드만 검색할 때 쓰임.
--레코드(row) 한 줄.: 컬럼(필드|field)들로 이루어져있음.
SELECT concat(count(*),'명') as "연봉이 2000이상인 직원"  FROM emp WHERE sal > 2000;
--큰따옴표(필드명), 작은따옴표(문자,날짜)
SELECT * FROM emp WHERE ename != 'KING'; -- != ~가 아닌것. NOT(<>로도 쓸 수 있음)
SELECT * FROM emp WHERE hiredate >= '1982/01/01'; --날짜비교 hiredate
SELECT * FROM emp WHERE deptno = 10 AND job = 'MANAGER'; -- AND 교집합
SELECT * FROM emp WHERE deptno = 10 OR job = 'MANAGER'; --OR 합집합
SELECT * FROM emp WHERE sal BETWEEN 2000 AND 3000; -- 반대: NOT BETWEEN
SELECT * FROM emp WHERE hiredate NOT BETWEEN '1981/01/01' AND '1981/12/31';
SELECT * FROM emp WHERE comm IN (300,500,1400); --IN 조건이 여러개일때, 반대: NOT IN
--LIKE 조회, 와일드카드(%) 조회(부분조회)
SELECT * FROM emp WHERE ename LIKE upper('f%'); --n%앞글자만 포함.upper소문자를 대문자로 검색.
SELECT * FROM emp WHERE ename LIKE lower('%K'); --%n뒷글자만 포함.lower대문자를 소문자로 검색
--NULL의 중요성: null값이 있으면 검색이 안됨.
--NULL값이 있을때 검색방법(아래)
SELECT * FROM emp WHERE comm IS NULL; -- IS NULL로 null값이 있는지 확인 방법.
--NVL(Null VaLue)널 값을 대치하는 함수
--사원중에 커미션을 0원 받고 한 사람 중 값이 null인 사람까지 검색하는 법.
SELECT * FROM emp WHERE NVL(comm,0) = 0;
SELECT NVL(comm,0), E.* FROM emp E WHERE NVL(comm,0) = 0; --NVL(comm,0)컬럼을 만들어서 null값에 0을 넣어서 출력
SELECT NVL2(comm,100,0), E.* FROM emp E WHERE NVL(comm,0) = 0; --NVL2(필드에서,null이면 0,null이 아니면 100)
--as E가 오류남 = E만 씀,오라클은 표준 쿼리가 아니라서. ANSI쿼리가 표준.
--DECODE(필드가,null일 때,0으로 채우고,널이 아닐때 이 값을 넣음)=NVL과 NVL2가 합쳐진 함수
SELECT 
CASE WHEN comm is null THEN 0 
WHEN comm = 0 THEN 100
WHEN comm > 0 THEN comm
END AS "CASE출력문"
,DECODE(comm,null,0,100)
,NVL2(comm,100,0)
,E.* FROM emp E;-- WHERE NVL(comm,0) = 0;
--연봉 기준으로 정렬 sort = 순서 order by 필드명 오름차순[초기값]|DESC 내림차순
SELECT * FROM emp ORDER BY sal; --오름차순
SELECT * FROM emp ORDER BY sal DESC; --내림차순
SELECT sal, E.* FROM emp E ORDER BY E.sal; --sal을 맨 앞으로 불러서 오름차순
--위 정렬에서 1등만 구할 limit는 Mysql(마리아DB)에 있는 명령어입니다.(오라클엔 없음)
--자동증가값(AI)도 오라클에 없음.
SELECT ROWNUM, E.* FROM (--테이블명
SELECT * FROM emp ORDER BY sal DESC
) E WHERE ROWNUM = 1; --내림차순으로 연봉이 많은 사람부터 적은 순으로 정렬.
--위와 같은 서브쿼리 문장 해석은 내부부터 합니다.
--ROWNUM: 오라클에서 1등 구하는 법.
--중복레코드(row)를 제거하는 명령어 distinct(아래)
SELECT deptno as "부서번호" FROM emp; --사원수대로 부서번호가 출력
SELECT DISTINCT deptno as "부서번호" FROM emp; --중복 제거.
-- 문자열을 연결할 때 concat 함수 외에 ||(파이프라인 2개 겹침)으로 구현
SELECT ename ||'is a'|| job AS "문자열 연결" FROM emp;
--여기까지 select 마무리 Read.
--이후 CRUD 중에 Insert, Update, Delete 3개의 쿼리로 실습 예정
--함수용어 ABS(Absolute절대값), Floor(바닥함수1.5=1)-ceil(천정함수1.5=2)
--ROUND(반올림), TRUNC(Truncate버리는함수), Mod(나머지 구하는 함수)
--Upper(대문자), Lower(소문자), Length(길이 구하는 함수)
--Instr(문자의 위치를 구하는 함수), Substr(매개 변수로 입력한 숫자위치 만큼 문자열을 추출)
--Lpad(왼쪽 여백), Rpad(오른쪽 여백) 레포트프로그램에서 출력 조정시 사용
--Trim(왼쪽,오른쪽 문자열을 잘라내는 함수)
--날짜 sysdate로 오라클 전용 함수, 게시물 입력 시간, 회원등록 시간 출력.
SELECT to_char(systimestamp, 'yyyy--mm-dd hh24:mi:ss:ff') From dual;
-- 위 to_char(날짜를 문자열로 변환) 형 변환 함수임.
SELECT sysdate + 1 FROM dual;
SELECT sysdate -1 FROM dual;
--아래는 6개월간 회원정보 수정이 없는 회원에게 메일발송 서비스를 처리
SELECT * FROM
TBL_MEMBER
WHERE UPDATE_DATE < ADD_MONTHS(SYSDATE,-6);