package life.pxx.community.service;

import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.User;
import life.pxx.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		
		UserExample example = new UserExample();
		example.createCriteria().andAccountIdEqualTo(user.getAccountId());
		List<User> users = userMapper.selectByExample(example);
		
		if (users.size() != 0) {
			User dbUser = users.get(0);
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			dbUser.setName(user.getName());
			dbUser.setToken(user.getToken());
			dbUser.setBio(user.getBio());
			userMapper.updateByPrimaryKeySelective(dbUser);
		} else {
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}
	}
}
