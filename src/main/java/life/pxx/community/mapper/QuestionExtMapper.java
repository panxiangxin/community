package life.pxx.community.mapper;

import life.pxx.community.model.Question;

public interface QuestionExtMapper {

	int updateByPrimaryKeyInc(Question question);
	int incCommentCount(Question question);
}