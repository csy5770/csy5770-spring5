package com.edu.dao;

import java.util.List;

import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 인터페이스는 회원관리에 관련 된 CRUD 메서드 명세가 포함 된 파일.
 * 이 인터페이스는 메서드명만 있고, {구현내용}이 없는 목록파일.
 * @author csy5770
 *
 */
public interface IF_MemberDAO {
	//List<제네릭타입> : MemberVO 1개 레코드를 List 클래스형으로 감싸줌.
	//다수의 레코드를 저장할 수 있게 됨.
	public List<MemberVO> selectMember(PageVO pageVO) throws Exception;
	//회원 전체 수를 구함(아래)
	public int countMember() throws Exception;
	public void insertMember(MemberVO memberVO) throws Exception; 
}
