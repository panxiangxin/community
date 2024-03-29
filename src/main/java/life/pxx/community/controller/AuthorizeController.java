package life.pxx.community.controller;

import life.pxx.community.dto.AccessTokenDTO;
import life.pxx.community.dto.GithubUser;
import life.pxx.community.model.User;
import life.pxx.community.provider.GithubProvider;
import life.pxx.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author pxx
 * Date 2019/9/27 13:55
 * @Description
 */
@Controller
@Slf4j
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	@Autowired
	private UserService userService;
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
						   @RequestParam(name = "state") String state,
						   HttpServletRequest request,
						   HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if (githubUser != null) {
			//写入数据库
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setToken(token);
			user.setBio(githubUser.getBio());
			user.setAvatarUrl(githubUser.getAvatar_url());
			userService.createOrUpdate(user);
			//写入cookie
			response.addCookie(new Cookie("token",token));
			
			return "redirect:/";
		}else {
			log.error("callback get github error,{}",githubUser);
			//登录失败 重新登录
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}
