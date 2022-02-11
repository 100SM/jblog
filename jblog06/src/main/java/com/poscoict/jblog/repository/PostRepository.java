package com.poscoict.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}

	public int update(PostVo postVo) {
		return sqlSession.update("post.update", postVo);
	}

	public int delete(Long no, Long categoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("CategoryNo", categoryNo);
		return sqlSession.delete("post.delete", map);
	}

	public List<PostVo> findByCategoryNo(Long categoryNo) {
		return sqlSession.selectList("post.findByCategoryNo", categoryNo);
	}

	public PostVo findByNoAndCategoryNo(Long no, Long categoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("categoryNo", categoryNo);
		return sqlSession.selectOne("post.findByNoAndCategoryNo", map);
	}

	public PostVo findRecentPost(Long categoryNo) {
		return sqlSession.selectOne("post.findRecentPost", categoryNo);
	}
}