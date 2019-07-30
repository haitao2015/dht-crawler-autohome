package cn.dht.crawler.autohome.service;

import java.util.List;

import cn.dht.crawler.autohome.module.CarTest;

public interface CarTestService {
	/***
	 * 车评数据查询
	 * @return
	 */
	public String  queryCarTest();
	/**
	 * 查询车评数据 分页
	 * @param page 当前页数
	 * @param rows 显示记录数
	 * @return
	 */
	public List<String> queryTitlByPage(int page, int rows);
	/***
	 * 保持车评数据到数据库
	 * @param carTest
	 */
	public void saveCarTest(CarTest carTest);

}
