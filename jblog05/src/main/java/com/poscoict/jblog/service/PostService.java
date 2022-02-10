package com.poscoict.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.PostRepository;
import com.poscoict.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;

	public boolean addContents(PostVo PostVo) {
		return postRepository.insert(PostVo) == 1;
	}

	public PostVo getContents(Long no, Long categoryNo) {
		return postRepository.findByNoAndCategoryNo(no, categoryNo);
	}

	public boolean updateContents(PostVo PostVo) {
		return postRepository.update(PostVo) == 1;
	}

	public boolean deleteContents(Long no, Long categoryNo) {
		return postRepository.delete(no, categoryNo) == 1;
	}

	public List<PostVo> getPostList(Long categoryNo) {
		return postRepository.findByCategoryNo(categoryNo);
	}

	public PostVo getRecentPost(Long categoryNo) {
		return postRepository.findRecentPost(categoryNo);
	}
}