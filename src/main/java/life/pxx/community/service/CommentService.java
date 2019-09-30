package life.pxx.community.service;

import life.pxx.community.enums.CommentTypeEnum;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.CommentMapper;
import life.pxx.community.mapper.QuestionExtMapper;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.model.Comment;
import life.pxx.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pxx
 * Date 2019/9/30 10:11
 * @Description
 */
@Service
public class CommentService {
	@Autowired
	CommentMapper commentMapper;
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	QuestionExtMapper questionExtMapper;
	
	public void insert(Comment record) {
		if (record.getParentId() == null || record.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if (record.getType() == null || !CommentTypeEnum.isExist(record.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		if (record.getType().equals(CommentTypeEnum.COMMENT.getType())) {
			//回复评论
			Comment dbComment = commentMapper.selectByPrimaryKey(record.getId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			commentMapper.insert(record);
		} else {
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(record.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(record);
			question.setCommentCount(1);
			System.out.println(question);
			questionExtMapper.incCommentCount(question);
		}
	}
}