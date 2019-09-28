package life.pxx.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author pxx
 * Date 2019/9/28 9:29
 * @Description
 */
@Controller
public class PublishController {

	@GetMapping("/publish")
	public String publish(){
		return "publish";
	}
}
