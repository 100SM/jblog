package com.poscoict.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.jblog.repository.PostRepository;
import com.poscoict.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	PostRepository PostRepository;

	public boolean addContents(PostVo PostVo) {
		return PostRepository.insert(PostVo) == 1;
	}

	public PostVo getContents(Long no, Long categoryNo) {
		PostVo PostVo = PostRepository.findByNoAndCategoryNo(no, categoryNo);
		return PostVo;
	}

	public boolean updateContents(PostVo PostVo) {
		return PostRepository.update(PostVo) == 1;
	}

	public boolean deleteContents(Long no, Long categoryNo) {
		return PostRepository.delete(no, categoryNo) == 1;
	}

	public List<PostVo> getContentsList(Long categoryNo) {
		return PostRepository.findByCategoryNo(categoryNo);
	}
}
