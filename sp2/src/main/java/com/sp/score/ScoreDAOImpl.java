package com.sp.score;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("score.scoreDAO")//데이터베이스 관련 객체 생성
public class ScoreDAOImpl implements ScoreDAO{
	@Autowired
	private SqlSession sqlSession;
	@Override
	public int insertScore(Score dto) {
		int result=0;
		try {
			result=sqlSession.insert("score.insertScore", dto);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result=0;
		try {							//네이스페이스.아이디, 파라미터타입
			result=sqlSession.selectOne("score.dataCount", map);
								//selectOne 은 한줄나올때 사용 map리턴
								//selectList 은 한줄 이상. 리스트 리턴.
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public <T> List<T> listScore(Map<String, Object> map) {
		List<T> list=null;
		try {
			list=sqlSession.selectList("score.listScore", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@Override
	public Score readScore(String hak) {
		Score dto=null;
		try {
			dto=sqlSession.selectOne("score.readScore", hak);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public int updateScore(Score dto) {
		int result=0;
		try {
			result=sqlSession.update("score.updateScore", dto);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}

	@Override
	public int deleteScore(String hak) {
		int result=0;
		try {
			result=sqlSession.delete("score.deleteScore", hak);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
}
