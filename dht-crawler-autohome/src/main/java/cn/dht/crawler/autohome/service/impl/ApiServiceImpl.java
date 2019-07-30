package cn.dht.crawler.autohome.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import cn.dht.crawler.autohome.service.ApiService;
@Service
public class ApiServiceImpl implements ApiService {

	private PoolingHttpClientConnectionManager cm;
	
	@Override
	public String getHtml(String url) {
		// 获取HttpClient对象;// 使用连接池管理器获取连接
		CloseableHttpClient httpClient=HttpClients.custom().setConnectionManager(cm).build();
		//声明httpGet请求对象
		HttpGet httpGet=new HttpGet(url);
		//设置用户代理信息
		httpGet.setHeader("User-Agent", "");
		//设置请求参数RequestConfig
		httpGet.setConfig(this.getConfig());
		
		//使用HttpClient发起请求，返回response;
		CloseableHttpResponse response=null;
		try {
			response=httpClient.execute(httpGet);
			//解析response返回的数据  $$$
			if(response.getStatusLine().getStatusCode()==200) {
				String html="";
				if(response.getEntity()!=null) {
					html=EntityUtils.toString(response.getEntity(), "UTF-8");
				}
				return html;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(response!=null) {
						response.close();
				}
				//不能关闭,现在使用的时连接管理器
				//httpClient.close();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		//解析response返回的数据  $$$
		
		
		return null;
	}

	@Override
	public String getImage(String url) {

		// 获取HttpClient对象;// 使用连接池管理器获取连接
		CloseableHttpClient httpClient=HttpClients.custom().setConnectionManager(cm).build();
		//声明httpGet请求对象
		HttpGet httpGet=new HttpGet(url);
		//设置用户代理信息
		httpGet.setHeader("User-Agent", "");
		//设置请求参数RequestConfig
		httpGet.setConfig(this.getConfig());
		
		//使用HttpClient发起请求，返回response;
		CloseableHttpResponse response=null;
		try {
			response=httpClient.execute(httpGet);
			//解析response 下载图片  $$$
			if(response.getStatusLine().getStatusCode()==200) {
				//使用UUID生成图片名称
				String uuid=UUID.randomUUID().toString();
				//image/gif
				String contentType=response.getEntity().getContentType().getValue();
				//获取文件类型
				String extName="."+contentType.split("/")[1];
				String imageName=uuid+extName;
				//声明输出的文件
				OutputStream outstream=new FileOutputStream(new File("D:/images/"+imageName));
				//使用响应体输出文件
				response.getEntity().writeTo(outstream);
				
				//返回生成的图片名
				return imageName;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(response!=null) {
						response.close();
				}
				//不能关闭,现在使用的时连接管理器
				//httpClient.close();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		//解析response返回的数据  $$$
		return null;
	}
	
	private RequestConfig getConfig() {
		RequestConfig config=RequestConfig.custom().setConnectTimeout(10000)//设置创建连接的超时时间；
				.setConnectionRequestTimeout(500)//设置获取连接的超时时间；
				.setSocketTimeout(10000)//设置连接的超时时间；
				.build();
		return config;
	}

}
