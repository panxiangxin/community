package life.pxx.community.controller;

import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.Question;
import life.pxx.community.model.User;
import life.pxx.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author pxx
 * Date 2019/9/27 12:03
 * @Description
 */
@Controller
public class GreetingController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/")
	public String greeting(HttpServletRequest request, Model model,
						   @RequestParam(value = "page",defaultValue = "1") Integer page,
						   @RequestParam(value = "size",defaultValue = "5") Integer size) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					User user = userMapper.findByToken(token);
					if (user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		PaginationDTO paginationDTO = questionService.list(page,size);
		model.addAttribute("pagination",paginationDTO);
			return "index";
	}
	
}
