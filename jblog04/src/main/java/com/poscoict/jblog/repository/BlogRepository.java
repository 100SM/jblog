package com.poscoict.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public int add(String userId) {
		return sqlSession.insert("blog.insert", userId);
	}

	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}

	public BlogVo findByUserId(String userId) {
		return sqlSession.selectOne("blog.findByUserId", userId);
	}
}