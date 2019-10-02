package life.pxx.community.service;

import life.pxx.community.dto.CommentDTO;
import life.pxx.community.enums.CommentTypeEnum;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.CommentMapper;
import life.pxx.community.mapper.QuestionExtMapper;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	UserMapper userMapper;
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	QuestionExtMapper questionExtMapper;
	
	public List<CommentDTO> listByQuestionId(Long id) {
		CommentExample example = new CommentExample();
		example.createCriteria()
				.andParentIdEqualTo(id)
				.andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
		example.setOrderByClause("gmt_create desc");
		List<Comment> comments = commentMapper.selectByExampleWithBLOBs(example);
		if(comments.size() == 0){
			return new ArrayList<>();
		}
		//获取去重的评论人id集合
		List<Long> userIds = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());
		
		UserExample example1 = new UserExample();
		example1.createCriteria()
				.andIdIn(userIds);
		//获取评论人以及转换为Map
		List<User> users = userMapper.selectByExample(example1);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
		
		//将Comment 转换为CommentDTO
		List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment,commentDTO);
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());
		
		return commentDTOS;
	}
	
	@Transactional
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
			questionExtMapper.incCommentCount(question);
		}
	}
}