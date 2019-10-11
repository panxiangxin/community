package life.pxx.community.mapper;

import life.pxx.community.dto.QueryQuestionDTO;
import life.pxx.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
	
	int updateByPrimaryKeyInc(Question question);
	
	int incCommentCount(Question question);
	
	List<Question> selectRelated(Question question);
	
	int countBySearch(QueryQuestionDTO queryQuestionDTO);
	
	List<Question> selectBySearch(QueryQuestionDTO queryQuestionDTO);
	
}