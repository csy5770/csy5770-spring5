--DESC: Description ���̺� ������ ����
DESC dept;

--SELECT: ���̺�����ȸ, where ��ȸ����, as(Alias)��Ī: �ʵ���� �� �� ���.
--concat: ����Ŭ ���� �Լ�. �ַ� ����Ʈ �ۼ��� ���� ��.
SELECT concat(deptno,'��') as "�μ���ȣ"
, dname as "�μ���" 
, concat(loc, '��') as "��ġ"
FROM dept 
WHERE loc = 'NEW YORK';
--DUAL �������̺� �̸�. ���̺��� ���� ������ select �� �� ���.
SELECT concat('��','ȿ��') FROM dual;
SELECT 3+5 as "3 ���ϱ� 8��" FROM dual;
--WHERE select�� �������� �� �� ����. ���ڵ常 �˻��� �� ����.
--���ڵ�(row) �� ��.: �÷�(�ʵ�|field)��� �̷��������.
SELECT concat(count(*),'��') as "������ 2000�̻��� ����"  FROM emp WHERE sal > 2000;
--ū����ǥ(�ʵ��), ��������ǥ(����,��¥)
SELECT * FROM emp WHERE ename != 'KING'; -- != ~�� �ƴѰ�. NOT(<>�ε� �� �� ����)
SELECT * FROM emp WHERE hiredate >= '1982/01/01'; --��¥�� hiredate
SELECT * FROM emp WHERE deptno = 10 AND job = 'MANAGER'; -- AND ������
SELECT * FROM emp WHERE deptno = 10 OR job = 'MANAGER'; --OR ������
SELECT * FROM emp WHERE sal BETWEEN 2000 AND 3000; -- �ݴ�: NOT BETWEEN
SELECT * FROM emp WHERE hiredate NOT BETWEEN '1981/01/01' AND '1981/12/31';
SELECT * FROM emp WHERE comm IN (300,500,1400); --IN ������ �������϶�, �ݴ�: NOT IN
--LIKE ��ȸ, ���ϵ�ī��(%) ��ȸ(�κ���ȸ)
SELECT * FROM emp WHERE ename LIKE upper('f%'); --n%�ձ��ڸ� ����.upper�ҹ��ڸ� �빮�ڷ� �˻�.
SELECT * FROM emp WHERE ename LIKE lower('%K'); --%n�ޱ��ڸ� ����.lower�빮�ڸ� �ҹ��ڷ� �˻�
--NULL�� �߿伺: null���� ������ �˻��� �ȵ�.
--NULL���� ������ �˻����(�Ʒ�)
SELECT * FROM emp WHERE comm IS NULL; -- IS NULL�� null���� �ִ��� Ȯ�� ���.
--NVL(Null VaLue)�� ���� ��ġ�ϴ� �Լ�
--����߿� Ŀ�̼��� 0�� �ް� �� ��� �� ���� null�� ������� �˻��ϴ� ��.
SELECT * FROM emp WHERE NVL(comm,0) = 0;
SELECT NVL(comm,0), E.* FROM emp E WHERE NVL(comm,0) = 0; --NVL(comm,0)�÷��� ���� null���� 0�� �־ ���
SELECT NVL2(comm,100,0), E.* FROM emp E WHERE NVL(comm,0) = 0; --NVL2(�ʵ忡��,null�̸� 0,null�� �ƴϸ� 100)
--as E�� ������ = E�� ��,����Ŭ�� ǥ�� ������ �ƴ϶�. ANSI������ ǥ��.
--DECODE(�ʵ尡,null�� ��,0���� ä���,���� �ƴҶ� �� ���� ����)=NVL�� NVL2�� ������ �Լ�
SELECT DECODE(comm,null,0,comm), E.* FROM emp E WHERE NVL(comm,0) = 0;
--���� �������� ���� sort = ���� order by �ʵ�� ��������[�ʱⰪ]|DESC ��������
SELECT * FROM emp ORDER BY sal; --��������
SELECT * FROM emp ORDER BY sal DESC; --��������
SELECT sal, E.* FROM emp E ORDER BY E.sal; --sal�� �� ������ �ҷ��� ��������
--�� ���Ŀ��� 1� ���� limit�� Mysql(������DB)�� �ִ� ��ɾ��Դϴ�.(����Ŭ�� ����)
--�ڵ�������(AI)�� ����Ŭ�� ����.
SELECT ROWNUM, E.* FROM (--���̺��
SELECT * FROM emp ORDER BY sal DESC
) E WHERE ROWNUM = 1; --������������ ������ ���� ������� ���� ������ ����.
--���� ���� �������� ���� �ؼ��� ���κ��� �մϴ�.
--ROWNUM: ����Ŭ���� 1�� ���ϴ� ��.
--�ߺ����ڵ�(row)�� �����ϴ� ��ɾ� distinct(�Ʒ�)
SELECT deptno as "�μ���ȣ" FROM emp; --�������� �μ���ȣ�� ���
SELECT DISTINCT deptno as "�μ���ȣ" FROM emp; --�ߺ� ����.
-- ���ڿ��� ������ �� concat �Լ� �ܿ� ||(���������� 2�� ��ħ)���� ����
SELECT ename ||'is a'|| job AS "���ڿ� ����" FROM emp;
--������� select ������ Read.
--���� CRUD �߿� Insert, Update, Delete 3���� ������ �ǽ� ����
