package com.poscoict.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.jblog.sercurity.AuthUser;
import com.poscoict.jblog.service.BlogService;
import com.poscoict.jblog.service.FileUploadService;
import com.poscoict.jblog.vo.BlogVo;
import com.poscoict.jblog.vo.UserVo;

@Controller
@RequestMapping("/jblog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("/{userId}")
	public String index(@PathVariable("userId") String userId, Model model) {
		BlogVo blogVo = blogService.getBlog(userId);
		model.addAttribute("blogVo", blogVo);
		return "blog/index";
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