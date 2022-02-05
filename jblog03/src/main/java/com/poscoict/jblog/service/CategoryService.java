package com.poscoict.jblog.service;

import java.util.List;

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

	public List<CategoryVo> findByBlogId(String blogId) {
		return categoryRepository.findByBlogId(blogId);
	}

	public CategoryVo findByNoAndBlogId(Long no, String blogId) {
		return categoryRepository.findByNoAndBlogId(no, blogId);
	}

	public boolean deleteCategory(CategoryVo categoryVo) {
		return categoryRepository.update(categoryVo) == 1;
	}
}