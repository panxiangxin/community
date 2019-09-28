package life.pxx.community.mapper;

import life.pxx.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
