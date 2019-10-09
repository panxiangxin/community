package life.pxx.community.service;

import life.pxx.community.dto.NotificationDTO;
import life.pxx.community.dto.PaginationDTO;
import life.pxx.community.dto.QuestionDTO;
import life.pxx.community.enums.NotificationEnum;
import life.pxx.community.enums.NotificationStatusEnum;
import life.pxx.community.exception.CustomizeErrorCode;
import life.pxx.community.exception.CustomizeException;
import life.pxx.community.mapper.NotificationMapper;
import life.pxx.community.mapper.UserMapper;
import life.pxx.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2019/10/9 18:55
 * @Description
 */
@Service
public class NotificationService {
	
	@Autowired
	private NotificationMapper notificationMapper;
	@Autowired
	private UserMapper userMapper;
	
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		
		PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria()
				.andReceiverEqualTo(userId);
		Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
		Integer totalPage;
		if(totalCount%size == 0){
			totalPage = totalCount/size;
		}else {
			totalPage = totalCount/size + 1;
		}
		if(page < 1){
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		paginationDTO.setPagination(totalPage,page);
		
		int offset = size*(page - 1);
		NotificationExample notificationExample1 = new NotificationExample();
		notificationExample1.createCriteria()
				.andReceiverEqualTo(userId);
		notificationExample1.setOrderByClause("gmt_create desc");
		List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample1, new RowBounds(offset, size));
		
		
		if (notifications.size() == 0) {
			return paginationDTO;
		}
		List<NotificationDTO> notificationDTOS=new ArrayList<>();
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification,notificationDTO);
			notificationDTO.setTypeName(NotificationEnum.getNameOf(notification.getType()));
			notificationDTOS.add(notificationDTO);
		}
		paginationDTO.setData(notificationDTOS);
		
		
		return paginationDTO;
	}
	
	public Long unReadCount(Long userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria()
				.andReceiverEqualTo(userId)
				.andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		return notificationMapper.countByExample(notificationExample);
	}
	
	public NotificationDTO read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		
		if (notification == null) {
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if (!notification.getReceiver().equals(user.getId())) {
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAILED);
		}
		NotificationDTO notificationDTO = new NotificationDTO();
		BeanUtils.copyProperties(notification,notificationDTO);
		notificationDTO.setTypeName(NotificationEnum.getNameOf(notification.getType()));
		
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		int i = notificationMapper.updateByPrimaryKey(notification);
		return notificationDTO;
	}
}
