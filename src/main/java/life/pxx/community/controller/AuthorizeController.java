package life.pxx.community.controller;

import life.pxx.community.dto.AccessTokenDTO;
import life.pxx.community.dto.GithubUser;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import life.pxx.community.provider.GithubProvider;
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
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	@Autowired
	private UserMapper userMapper;
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
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			user.setBio(githubUser.getBio());
			user.setAvatarUrl(githubUser.getAvatar_url());
			userMapper.insert(user);
			//写入cookie
			response.addCookie(new Cookie("token",token));
			
			return "redirect:/";
		}else {
			//登录失败 重新登录
			return "redirect:/";
		}
	}
}
