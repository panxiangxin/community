package life.pxx.community.service;

import life.pxx.community.dto.CommentDTO;
import life.pxx.community.enums.CommentTypeEnum;
import life.pxx.community.enums.NotificationEnum;
import life.pxx.community.enums.NotificationStatusEnum;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.*;
import life.pxx.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2019/9/30 10:11
 * @Description
 */
@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private CommentExtMapper commentExtMapper;
	@Autowired
	private NotificationMapper notificationMapper;
	
	public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
		CommentExample example = new CommentExample();
		example.createCriteria()
				.andParentIdEqualTo(id)
				.andTypeEqualTo(type.getType());
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
	public void insert(Comment record, User commentator) {
		if (record.getParentId() == null || record.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if (record.getType() == null || !CommentTypeEnum.isExist(record.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		if (record.getType().equals(CommentTypeEnum.COMMENT.getType())) {
			//回复评论
			Comment dbComment = commentMapper.selectByPrimaryKey(record.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insertSelective(record);
			//增加评论数
			Comment parentComment = new Comment();
			parentComment.setId(record.getParentId());
			parentComment.setCommentCount(1);
			commentExtMapper.incCommentCount(parentComment);
			//增加通知消息
			createNotify(record, dbComment.getCommentator(), NotificationEnum.REPLY_COMMENT, question.getTitle(), commentator.getName(), question.getId());
			
		} else {
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(record.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insertSelective(record);
			question.setCommentCount(1);
			questionExtMapper.incCommentCount(question);
			
			//创建通知
			createNotify(record,question.getCreator(),NotificationEnum.REPLY_QUESTION, commentator.getName(),question.getTitle(), question.getId());
		}
	}
	
	private void createNotify(Comment record, Long receiver, NotificationEnum notificationEnum, String notifierName, String outerTitle, Long outerId) {
		
		if (receiver.equals(record.getCommentator())) {
			return;
		}
		Notification notification = new Notification();
		notification.setNotifier(record.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver);
		notification.setOuterid(outerId);
		notification.setType(notificationEnum.getType());
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}
}