package com.poscoict.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.CategoryRepository;
import com.poscoict.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public void addCategory(CategoryVo categoryVo) {
		categoryRepository.add(categoryVo);
	}

	public CategoryVo findByBlogId(String blogId) {
		return categoryRepository.findByBlogId(blogId);
	}

	public boolean deleteCategory(CategoryVo categoryVo) {
		return categoryRepository.update(categoryVo) == 1;
	}
}