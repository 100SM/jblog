package com.poscoict.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.BlogRepository;
import com.poscoict.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public void addBlog(String userId) {
		blogRepository.add(userId);
	}

	public BlogVo getBlog(String userId) {
		return blogRepository.findByUserId(userId);
	}

	public boolean updateBlog(BlogVo blogVo) {
		return blogRepository.update(blogVo) == 1;
	}
}