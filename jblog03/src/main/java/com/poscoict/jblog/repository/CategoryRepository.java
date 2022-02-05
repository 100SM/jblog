package com.poscoict.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;

	public int add(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}

	public int update(CategoryVo categoryVo) {
		return sqlSession.update("category.update", categoryVo);
	}

	public List<CategoryVo> findByBlogId(String blogId) {
		return sqlSession.selectList("category.findByBlogId", blogId);
	}

	public int delete(Long no, String blogId) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("blogId", blogId);
		return sqlSession.delete("category.delete", map);
	}

	public CategoryVo findByNoAndBlogId(Long no, String blogId) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("blogId", blogId);
		return sqlSession.selectOne("category.findByNoAndBlogId", map);
	}
}