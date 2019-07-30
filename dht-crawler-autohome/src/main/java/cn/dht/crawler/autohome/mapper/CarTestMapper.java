package cn.dht.crawler.autohome.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.dht.crawler.autohome.module.CarTest;
@Mapper
public interface CarTestMapper {
	
	/***
	 * 查询汽车标题数据  分页
	 * @param map
	 * @return
	 */
	@Select("SELECT title from car_test limit #{start},#{rows} ")
	public List<String> queryTitlByPage(Map<String, Object> map);
	@Insert(
			"INSERT INTO `car_test` (" +
			"	`title`," +
			"	`test_speed`," +
			"	`test_brake`," +
			"	`test_oil`," +
			"	`editor_name1`," +
			"	`editor_remark1`," +
			"	`editor_name2`," +
			"	`editor_remark2`," +
			"	`editor_name3`," +
			"	`editor_remark3`," +
			"	`image`," +
			"	`created`," +
			"	`updated`" +
			")" +
			"VALUES" +
			"	(" +
			"		#{title}," +
			"		#{test_speed}," +
			"		#{test_brake}," +
			"		#{test_oil}," +
			"		#{editor_name1}," +
			"		#{editor_remark1}," +
			"		#{editor_name2}," +
			"		#{editor_remark2}," +
			"		#{editor_name3}," +
			"		#{editor_remark3}," +
			"		#{image}," +
			"		#{created}," +
			"		#{updated}" +
			"	)")
	public void save(CarTest carTest);

}
