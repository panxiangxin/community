package life.pxx.community.controller;

import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pxx
 * Date 2019/9/27 12:03
 * @Description
 */
@Controller
public class GreetingController {
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
	public String greeting(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie:cookies) {
			if("token".equals(cookie.getName())){
				String token = cookie.getValue();
				User user = userMapper.findByToken(token);
				if (user != null) {
					request.getSession().setAttribute("user",user);
				}
				break;
			}
		}
		return "index";
	}
	
}
