package cn.dht.crawler.autohome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dht.crawler.autohome.service.TestService;

@RestController
//@RequestMapping("api")
public class TestController {
	@Autowired
	private TestService testService;

	@RequestMapping("date")
	public String queryDate() {
		//// http://127.0.0.1:8080/date
		String date = testService.queryDate();
		return date;
	}
}
