package life.pxx.community.provider;

import com.alibaba.fastjson.JSON;
import life.pxx.community.dto.AccessTokenDTO;
import life.pxx.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;



/**
 * @author pxx
 * Date 2019/9/27 14:03
 * @Description
 */
@Component
public class GithubProvider {
	
	public String getAccessToken(AccessTokenDTO accessTokenDTO){
		
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
			Request request = new Request.Builder()
									  .url("https://github.com/login/oauth/access_token")
									  .post(body)
									  .build();
			try (Response response = client.newCall(request).execute()) {
				String string = response.body().string();
				return string.split("&")[0].split("=")[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	public GithubUser getUser(String accessToken){
		OkHttpClient client = new OkHttpClient();
		
			Request request = new Request.Builder()
									  .url("https://api.github.com/user?access_token="+accessToken)
									  .build();
			try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
				return JSON.parseObject(string,GithubUser.class);
		} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
	}
	
}
