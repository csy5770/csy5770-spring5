package com.edu.service;

import java.util.List;

import com.edu.vo.MemberVO;
import com.edu.vo.PageVO;

/**
 * 이 인터페이스는 회원관리와 관련 된 Service의 명세를 모아놓은 파일.
 * {구현내용} 없음.
 * @author csy5770
 *
 */
public interface IF_MemberService {
	public List<MemberVO> selectMember(PageVO pageVO) throws Exception;
	// 이곳에 CRUD 메서드가 계속 추가 됨.
	public int countMember() throws Exception;
	public void insertMember(MemberVO memberVO) throws Exception;
	public void deleteMember(String user_id) throws Exception;
}
