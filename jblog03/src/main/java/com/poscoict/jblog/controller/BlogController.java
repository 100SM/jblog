package com.poscoict.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.jblog.security.AuthUser;
import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.CategoryService;
import com.poscoict.jblog.service.FileUploadService;
import com.poscoict.jblog.service.PostService;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.PostVo;
import com.poscoict.jblog.vo.UserVo;

@Controller
@RequestMapping("/jblog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("/{userId}")
	public String index(@PathVariable("userId") String userId, Model model) {
		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		PostVo recentPostVo = postService.getRecentPost(categoryVoList.get(0).getNo());
		model.addAttribute("recentPostVo", recentPostVo);

		return "blog/blog-main";
	}

	// @Auth(블로그주인이면)
	@RequestMapping(value = "/{userId}/admin/basic", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, BlogVo BlogVo,
			@RequestParam(value = "logo-file") MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile);
		BlogVo.setLogo(url);
		blogService.updateBlog(BlogVo);

		return "redirect:/jblog";
	}
}