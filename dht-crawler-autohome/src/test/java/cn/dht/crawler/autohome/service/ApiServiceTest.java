package cn.dht.crawler.autohome.service;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.dht.crawler.autohome.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ApiServiceTest {
	@Autowired
	private ApiService apiService;

	@Test
	public void testGetHtml() {
		String url="https://www.autohome.com.cn/bestauto/1";
		String html =this.apiService.getHtml(url);
		Document dom=Jsoup.parse(html);
		System.out.println(dom.select("title").first().text());
	}

	@Test
	public void testGetImage() {
//		fail("Not yet implemented");
		String imageName=this.apiService.getImage("https://car0.autoimg.cn/cardfs/product/g3/M01/D5/CD/s_autohomecar__ChsEkV0WA-mAbBFnAAetS3Wb7h4821.jpg");
		System.out.println(imageName);
	}

}
