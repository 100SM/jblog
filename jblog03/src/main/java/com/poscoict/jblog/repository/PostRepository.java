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

	public int insert(PostVo PostVo) {
		return sqlSession.insert("post.insert", PostVo);
	}

	public int update(PostVo PostVo) {
		return sqlSession.update("post.update", PostVo);
	}

	public int delete(Long no, Long CategoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("CategoryNo", CategoryNo);
		return sqlSession.delete("post.delete", map);
	}

	public List<PostVo> findByCategoryNo(Long CategoryNo) {
		return sqlSession.selectList("post.findByCategoryNo");
	}

	public PostVo findByNoAndCategoryNo(Long no, Long CategoryNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("CategoryNo", CategoryNo);
		return sqlSession.selectOne("post.findByNoAndCategoryNo", map);
	}

	public PostVo findRecentPost(Long CategoryNo) {
		return sqlSession.selectOne("post.findRecentPost");
	}
}
