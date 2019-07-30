package cn.dht.crawler.autohome.service;

public interface ApiService {
	/**
	 * 1.GET请求获取页面数据
	 * @param url
	 * @return
	 */
	public String getHtml (String url); 
	/***
	 * 2.get请求获取图片
	 * @param url
	 * @return
	 */
	public String getImage (String url) ;
	
}
