package life.pxx.community.schedule;

import life.pxx.community.cache.HotTagCache;
import life.pxx.community.dto.HotTagDTO;
import life.pxx.community.mapper.QuestionMapper;
import life.pxx.community.model.Question;
import life.pxx.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author pxx
 * Date 2019/10/11 15:27
 * @Description
 */
@Component
@Slf4j
public class HotTagTasks {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private HotTagCache hotTagCache;
	
	@Scheduled(fixedRate = 20000)
	//@Scheduled(cron = " 0 0 1 * * *")
	public void hotTagSchedule() {
		int offset = 0;
		int limit = 5;
		log.info("hot schedule start {}", new Date());
		List<Question> list = new ArrayList<>();
		Map<String, HotTagDTO> priorities = new HashMap<>();
		while (offset == 0 || list.size() == limit) {
			list = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
			list.forEach(question -> {
				String[] tags = StringUtils.split(question.getTag(), ",");
				for (String tag : tags) {
					HotTagDTO priority = priorities.get(tag);
					if (priority != null) {
						priority.setQuestionCount(priority.getQuestionCount() + 1);
						priority.setCommentCount(priority.getCommentCount()+ question.getCommentCount());
					}else {
						priority = new HotTagDTO();
						priority.setName(tag);
						priority.setQuestionCount(1);
						priority.setCommentCount(question.getCommentCount());
					}
					priorities.put(tag,priority);
				}
			});
			offset += limit;
		}
		log.info("hot schedule end {}", new Date());
		hotTagCache.updateTags(priorities);
	}
}
