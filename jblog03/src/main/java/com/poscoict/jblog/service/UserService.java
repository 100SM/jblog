package com.poscoict.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.BlogRepository;
import com.poscoict.jblog.repository.CategoryRepository;
import com.poscoict.jblog.repository.UserRepository;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;
//	@Autowired
//	private CategoryVo categoryVo;

	public boolean join(UserVo userVo) {
		userRepository.insert(userVo);
		System.out.println("UserService 25 line : userVo : " + userVo);
		blogRepository.add(userVo.getId());
		System.out.println("UserService 27 line : userVo.getId() : " + userVo.getId());
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(userVo.getId());
		categoryRepository.add(categoryVo);
		return userRepository.insert(userVo) == 1;
	}

	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}

	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}
}