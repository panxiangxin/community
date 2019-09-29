package life.pxx.community.service;

import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pxx
 * Date 2019/9/29 12:50
 * @Description
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	
	public void createOrUpdate(User user) {
	
		User dbUser = userMapper.findByAccountId(user.getAccountId());
		if (dbUser.getAccountId() != null) {
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			dbUser.setName(user.getName());
			dbUser.setToken(user.getToken());
			dbUser.setBio(user.getBio());
			userMapper.update(dbUser);
		}else {
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}
	}
}
