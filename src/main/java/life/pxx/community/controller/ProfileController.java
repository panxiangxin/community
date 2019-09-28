package life.pxx.community.controller;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import life.pxx.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author pxx
 * Date 2019/9/28 17:15
 * @Description
 */
@Controller
public class ProfileController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name = "action") String action,
						  @RequestParam(name = "page",defaultValue = "1") Integer page,
						  @RequestParam(name = "size",defaultValue = "5") Integer size,
						  Model model,
						  HttpServletRequest request){
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					 user = userMapper.findByToken(token);
					if (user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		if (user == null) {
			return "redirect:/";
		}
		String section = "";
		String sectionName = "";
		if("questions".equals(action)){
			section = "questions";
			sectionName = "我的提问";
		}
		if ("replies".equals(action)) {
			section = "replies";
			sectionName = "最新回复";
		}
		PaginationDTO list = questionService.list(user.getId(), page, size);
		model.addAttribute("section",section);
		model.addAttribute("sectionName",sectionName);
		model.addAttribute("pagination",list);
		
		return "profile";
	}
}
