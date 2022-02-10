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

import com.poscoict.jblog.security.Auth;
import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.CategoryService;
import com.poscoict.jblog.service.FileUploadService;
import com.poscoict.jblog.service.PostService;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.CategoryVo;
import com.poscoict.jblog.vo.PostVo;

@Controller
@RequestMapping("/jblog/{userId}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String main(@PathVariable("userId") String userId, Model model) {
		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		PostVo recentPostVo = postService.getRecentPost(categoryVoList.get(0).getNo());
		model.addAttribute("PostVo", recentPostVo);
		Long categoryNo = categoryService.getRecentCategoryNo(userId);
		model.addAttribute("categoryNo", categoryNo);

		return "blog/blog-main";
	}

	@RequestMapping("/{categoryNo}")
	public String main(@PathVariable("userId") String userId, @PathVariable("categoryNo") Long categoryNo,
			Model model) {
		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryNo);
		model.addAttribute("postVoList", postVoList);
		PostVo recentPostVo = postService.getRecentPost(categoryNo);
		model.addAttribute("PostVo", recentPostVo);
		return "blog/blog-main";
	}

	@RequestMapping("/{categoryNo}/{postNo}")
	public String main(@PathVariable("userId") String userId, @PathVariable("categoryNo") Long categoryNo,
			@PathVariable("postNo") Long postNo, Model model) {
		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		if (categoryNo == null)
			categoryNo = categoryService.getRecentCategoryNo(userId);
		List<PostVo> postVoList = postService.getPostList(categoryNo);
		model.addAttribute("postVoList", postVoList);
		if (postNo == null)
			postNo = postService.getRecentPost(categoryNo).getNo();
		PostVo PostVo = postService.getContents(postNo, categoryNo);
		model.addAttribute("PostVo", PostVo);

		return "blog/blog-main";
	}

	@Auth
	@RequestMapping(value = "/admin/basic")
	public String adminBasic(@PathVariable("userId") String userId, BlogVo blogVo, Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		return "blog/blog-admin-basic";
	}

	@Auth
	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String adminBasic(@PathVariable("userId") String userId, BlogVo blogVo,
			@RequestParam(value = "logo-file") MultipartFile multipartFile) {
		String url = fileUploadService.restore(multipartFile);
		blogVo.setLogo(url);
		blogService.updateBlog(blogVo);

		return "redirect:/jblog/{userId}";
	}

	@Auth
	@RequestMapping(value = "/admin/category")
	public String adminCategory(@PathVariable("userId") String userId, BlogVo blogVo, Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);

		return "blog/blog-admin-category";
	}

	@Auth
	@RequestMapping(value = "/admin/category/delete/{categoryNo}")
	public String adminCategoryDelete(@PathVariable("userId") String userId, BlogVo blogVo,
			@PathVariable("categoryNo") Long categoryNo, Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		if (categoryService.findByNoAndBlogId(categoryNo, userId).getPostCount() > 0
				|| categoryService.findByBlogId(userId).size() == 1) {
			return "redirect:/jblog/{userId}/admin/category";
		}
		categoryService.deleteCategory(categoryNo, userId);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		return "redirect:/jblog/{userId}/admin/category";
	}

	@Auth
	@RequestMapping(value = "/admin/category/add", method = RequestMethod.POST)
	public String adminCategory(@PathVariable("userId") String userId, BlogVo blogVo, CategoryVo categoryVo,
			Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		categoryVo.setBlogId(userId);
		categoryService.addCategory(categoryVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		return "redirect:/jblog/{userId}/admin/category";
	}

	@Auth
	@RequestMapping(value = "/admin/write")
	public String adminWrite(@PathVariable("userId") String userId, BlogVo blogVo, CategoryVo categoryVo, Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		return "blog/blog-admin-write";
	}

	@Auth
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String adminWrite(@PathVariable("userId") String userId, BlogVo blogVo, Long categoryNo, PostVo postVo,
			Model model) {
		blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> categoryVoList = categoryService.findByBlogId(blogVo.getUserId());
		model.addAttribute("categoryVoList", categoryVoList);
		postVo.setCategoryNo(categoryNo);
		postService.addContents(postVo);
		List<PostVo> postVoList = postService.getPostList(categoryVoList.get(0).getNo());
		model.addAttribute("postVoList", postVoList);
		PostVo recentPostVo = postService.getRecentPost(categoryVoList.get(0).getNo());
		model.addAttribute("PostVo", recentPostVo);
		categoryNo = categoryService.getRecentCategoryNo(userId);
		model.addAttribute("categoryNo", categoryNo);
		return "redirect:/jblog/{userId}";
	}
}