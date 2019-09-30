package life.pxx.community.mapper;

import life.pxx.community.model.Question;
import life.pxx.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    
    int updateByPrimaryKeyInc(Question question);
}