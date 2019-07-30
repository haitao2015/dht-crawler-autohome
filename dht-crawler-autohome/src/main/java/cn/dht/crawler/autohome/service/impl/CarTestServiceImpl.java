package cn.dht.crawler.autohome.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dht.crawler.autohome.mapper.CarTestMapper;
import cn.dht.crawler.autohome.module.CarTest;
import cn.dht.crawler.autohome.service.CarTestService;
@Service
public class CarTestServiceImpl  implements CarTestService{
	@Autowired
	private CarTestMapper carTestMapper;
	@Override
	public String queryCarTest() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> queryTitlByPage(int page, int rows) {
		// TODO Auto-generated method stub
		//limit
		int start=(page-1)*rows;//开始条数
		Map<String,Object> map=new HashMap<>();
			map.put("start", start);
			map.put("rows", rows);
			
		List<String> list=carTestMapper.queryTitlByPage(map);
		return list;
	}
	@Override
	public void saveCarTest(CarTest carTest) {
		carTestMapper.save(carTest);
		
	}
	

}
