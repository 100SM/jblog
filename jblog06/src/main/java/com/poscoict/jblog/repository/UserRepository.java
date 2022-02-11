package com.poscoict.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.jblog.exception.UserRepositoryException;
import com.poscoict.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;

	public int insert(UserVo userVo) {
		return sqlSession.insert("user.insert", userVo);
	}

	public UserVo findByIdAndPassword(String Id, String password) throws UserRepositoryException {
		Map<String, String> map = new HashMap<>();
		map.put("id", Id);
		map.put("password", password);
		return sqlSession.selectOne("user.findByIdAndPassword", map);
	}

	public UserVo findById(String id) {
		return sqlSession.selectOne("user.findById", id);
	}
}