package life.pxx.community.mapper;

import life.pxx.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author pxx
 * Date 2019/9/27 16:41
 * @Description
 */
@Mapper
public interface UserMapper {
	@Insert("insert into USER (NAME,ACCOUNT_ID,TOKEN,GMT_CREATE,GMT_MODIFIED,BIO) values " +
					"(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio})")
	void insert(User user);
	
	@Select("select * from USER where TOKEN = #{token}")
	User findByToken(@Param("token") String token);
}
