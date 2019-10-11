package life.pxx.community.cache;

import life.pxx.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author pxx
 * Date 2019/10/11 15:57
 * @Description
 */
@Data
@Component
public class HotTagCache {
	private List<HotTagDTO> hotTags = new ArrayList<>();
	
	public void updateTags(Map<String, HotTagDTO> tags) {
		int max = 5;
		PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
		
		tags.forEach((name,priority)->{
			
			if(priorityQueue.size() < max){
				priorityQueue.add(priority);
			}else {
				HotTagDTO minTag = priorityQueue.peek();
				if (priority.compareTo(minTag) > 0) {
					priorityQueue.poll();
					priorityQueue.add(priority);
				}
			}
		});
		List<HotTagDTO> list = new ArrayList<>();
		while (priorityQueue.size() != 0) {
			HotTagDTO hotTagDTO = priorityQueue.poll();
			list.add(0,hotTagDTO);
		}
		hotTags = list;
	}
	
}
