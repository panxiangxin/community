package life.pxx.community.mapper;

import life.pxx.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author pxx
 * Date 2019/9/28 10:31
 * @Description
 */
@Mapper
public interface QuestionMapper {
	
	@Insert("insert into QUESTION(title, description, gmt_create, gmt_modified, creator ,tag) values" +
					" (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	void create(Question question);
	
	@Select("select * from QUESTION limit #{offset},#{size}")
	List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);
	
	@Select("select count(1) from QUESTION")
	Integer count();
	
	@Select("select * from QUESTION where CREATOR=#{userId} limit #{offset},#{size}")
	List<Question> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);
	
	@Select("select count(1) from QUESTION where CREATOR = #{userId}")
	Integer countByUserId(@Param("userId") Integer userId);
	
	@Select("select * from QUESTION where ID = #{id}")
	Question getById(@Param("id") Integer id);
	
	@Update("update QUESTION set TITLE = #{title},DESCRIPTION = #{description},GMT_MODIFIED = #{gmtModified},TAG = #{tag} where ID = #{id}")
	void update(Question question);
}
