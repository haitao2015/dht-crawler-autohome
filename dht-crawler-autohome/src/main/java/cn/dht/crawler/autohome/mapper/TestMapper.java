package cn.dht.crawler.autohome.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 测试Mapper接口
 * @author Administrator
 *
 */
@Mapper
public interface TestMapper {
	/**
	 * 查询数据库时间
	 * @return
	 */
	@Select("SELECT Now()")
	public String queryDate();

}
