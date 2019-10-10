package life.pxx.community.interceptor;

import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import life.pxx.community.model.UserExample;
import life.pxx.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author pxx
 * Date 2019/9/29 8:59
 * @Description
 */
@Service
public class SectionInterception implements HandlerInterceptor {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NotificationService notificationService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					UserExample example = new UserExample();
					example.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(example);
					if (users.size() != 0) {
						request.getSession().setAttribute("user", users.get(0));
						Long unReadCount = notificationService.unReadCount(users.get(0).getId());
						request.getSession().setAttribute("unReadCount",unReadCount);
					}
					break;
				}
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	
	}
}
