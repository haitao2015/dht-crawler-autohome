package cn.dht.crawler.autohome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dht.crawler.autohome.mapper.TestMapper;
import cn.dht.crawler.autohome.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper testMapper;
	@Override
	public String queryDate() {
		String date=this.testMapper.queryDate();
		return date;
	}

}
