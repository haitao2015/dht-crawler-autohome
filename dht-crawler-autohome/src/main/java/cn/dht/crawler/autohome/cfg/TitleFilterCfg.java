package cn.dht.crawler.autohome.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.dht.crawler.autohome.service.CarTestService;
import cn.dht.crawler.autohome.util.TitleFilter;

@Configuration
public class TitleFilterCfg {
	@Autowired
	private CarTestService carTestService;

	@Bean
	public TitleFilter titleFiler() {
		
		TitleFilter titleFilter = new TitleFilter();
		//声明页码数
		int page=1,pageSize=0;
		do {
			//查询数据库中的标题数据
			List<String> titles= this.carTestService.queryTitlByPage(page,500);
			
			//添加到过滤器
			for (String titl : titles) {
				//把已有的数据 初始化ADD到过滤器
				titleFilter.add(titl);
			}
			page++;//下一页
			pageSize=titles.size();
		}while(pageSize==500);

		return titleFilter;

	}

}
