package cn.dht.crawler.autohome.job;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

//同步执行任务(单线程)
@DisallowConcurrentExecution
public class CloseConnectJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// 获取spring容器
		ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap()
				.get("context");

		// 从容器中获取HttpClient连接管理器
		PoolingHttpClientConnectionManager cm = applicationContext.getBean(PoolingHttpClientConnectionManager.class);

		// 关闭失效连接
		cm.closeExpiredConnections();
		System.out.println("关闭失效连接");
	}

}