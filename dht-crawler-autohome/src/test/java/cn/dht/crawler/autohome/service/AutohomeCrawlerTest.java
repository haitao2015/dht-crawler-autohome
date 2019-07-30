package cn.dht.crawler.autohome.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.dht.crawler.autohome.Application;
import cn.dht.crawler.autohome.module.CarTest;
import cn.dht.crawler.autohome.util.TitleFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class AutohomeCrawlerTest {
	@Autowired
	private ApiService apiService;

	@Autowired
	private CarTestService carTestService;

	@Autowired
	private TitleFilter titleFilter;

	@Test
	public void testCrawlerAutohome() throws Exception {
		//遍历所有的url
		for (int i = 1; i < 139; i++) {

			String html = this.apiService.getHtml("https://www.autohome.com.cn/bestauto/" + i);

			Document doc = Jsoup.parse(html);

			// 获取每获取评测信息
			Elements cars = doc.select("#bestautocontent div.uibox");

			// 遍历评测信息
			for (Element car : cars) {
				// 去重判读
				String title = car.getElementsByClass("uibox-title uibox-title-border").text();
				if (this.titleFilter.contains(title)) {
					// 如果包含了，就不保存了，遍历下一个
					continue;
				}

				// 创建评测对象,封装数据
				CarTest carTest = this.copyCarTest(car);

				// 评测图片，下载图片
				String image = this.getImage(car);

				// 设置图片
				carTest.setImage(image);

				// 保存数据
				this.saveCarTest(carTest);
			}
		}
	}
	/**
	 * 保存汽车评测数据
	 * 
	 * @param carTest
	 */
	private void saveCarTest(CarTest carTest) {

		this.titleFilter.add(carTest.getTitle());

		this.carTestService.saveCarTest(carTest);

	}
	
	/**
	 * 解析数据下载评测图片
	 * 
	 * @param car
	 * @return
	 */
	private String getImage(Element car) {
		List<String> images = new ArrayList<String>();

		Elements elements = car.select(".piclist-box ul.piclist02 a");
		for (Element element : elements) {
			String url = "https:" + element.attr("href");

			String html = this.apiService.getHtml(url);
			Document doc = Jsoup.parse(html);
			String picUrl = "https:" + doc.select("#img").attr("src");

			String image = this.apiService.getImage(picUrl);

			images.add(image);

			break;
		}

		return StringUtils.join(images, ",");
	}
	/**
	 * 解析数据封装成汽车评测对象
	 * 
	 * @param car
	 * @return
	 */
	private CarTest copyCarTest(Element car) {
		CarTest carTest = new CarTest();

		// 评测车辆标题
		String title = car.getElementsByClass("uibox-title uibox-title-border").text();
		carTest.setTitle(title);

		// 评测项目-加速(0-100公里/小时),单位毫秒
		String speed = car.select(".tabbox1 dd:nth-child(2) div.dd-div2").first().text();
		carTest.setTest_speed(this.strToNum(speed));

		// 评测项目-刹车(100-0公里/小时),单位毫米
		String brake = car.select(".tabbox1 dd:nth-child(3) div.dd-div2").first().text();
		carTest.setTest_brake(this.strToNum(brake));

		// 评测项目-实测油耗(升/100公里),单位毫升
		String oil = car.select(".tabbox1 dd:nth-child(4) div.dd-div2").first().text();
		carTest.setTest_oil(this.strToNum(oil));

		// 评测编辑1
		carTest.setEditor_name1(car.select(".tabbox2 dd:nth-child(2) > div.dd-div1").first().text());
		// 点评内容1
		carTest.setEditor_remark1(car.select(".tabbox2 dd:nth-child(2) > div.dd-div3").first().text());

		// 评测编辑2
		carTest.setEditor_name2(car.select(".tabbox2 dd:nth-child(3) > div.dd-div1").first().text());
		// 点评内容2
		carTest.setEditor_remark2(car.select(".tabbox2 dd:nth-child(3) > div.dd-div3").first().text());

		// 评测编辑3
		carTest.setEditor_name3(car.select(".tabbox2 dd:nth-child(4) > div.dd-div1").first().text());
		// 点评内容3
		carTest.setEditor_remark3(car.select(".tabbox2 dd:nth-child(4) > div.dd-div3").first().text());

		// 设置时间
		carTest.setCreated(new Date());
		carTest.setUpdated(carTest.getCreated());

		return carTest;
	}

	/**
	 * 把字符串去掉最后一个数，转为乘以1000的数字
	 * 
	 * @param speed
	 * @return
	 */
	private int strToNum(String str) {
		try {
			// 字符串去掉随后一个数
			str = StringUtils.substring(str, 0, str.length() - 1);

			// 转换为小数并乘以1000
			Number num = Float.valueOf(str) * 1000;

			return num.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(str);
		}
		return 0;
	}

}
