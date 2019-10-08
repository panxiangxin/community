package life.pxx.community.mapper;

import life.pxx.community.model.Comment;

public interface CommentExtMapper {
	
	int incCommentCount(Comment comment);
}