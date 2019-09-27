package life.pxx.community.controller;

import life.pxx.community.dto.AccessTokenDTO;
import life.pxx.community.dto.GithubUser;
import life.pxx.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pxx
 * Date 2019/9/27 13:55
 * @Description
 */
@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
						   @RequestParam(name = "state") String state) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser user = githubProvider.getUser(accessToken);
		System.out.println(user);
		return "index";
	}
}
