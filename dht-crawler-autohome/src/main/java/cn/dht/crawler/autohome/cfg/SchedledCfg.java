package cn.dht.crawler.autohome.cfg;

import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import cn.dht.crawler.autohome.job.CloseConnectJob;
import cn.dht.crawler.autohome.job.CrawlerAutohomeJob;

@Configuration
public class SchedledCfg {

	// 定义关闭无效连接任务
	@Bean("closeConnectJobBean")
	public JobDetailFactoryBean closeConnectJobBean() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setApplicationContextJobDataKey("context");
		jobDetailFactoryBean.setJobClass(CloseConnectJob.class);// 任务bean
		jobDetailFactoryBean.setDurability(true);

		return jobDetailFactoryBean;
	}

// 定义关闭无效连接触发器
	@Bean("closeConnectJobTrigger")
	public CronTriggerFactoryBean closeConnectJobTrigger(
			@Qualifier(value = "closeConnectJobBean") JobDetailFactoryBean itemJobBean) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(itemJobBean.getObject());
		tigger.setCronExpression("0/5 * * * * ? ");
		return tigger;
	}

	// 任务二 抓取车评数据 任务
	@Bean("crawlerAutohomeJobBean")
	public JobDetailFactoryBean crawlerAutohomeJobBean() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setApplicationContextJobDataKey("context");
		jobDetailFactoryBean.setJobClass(CrawlerAutohomeJob.class);// 任务bean
		jobDetailFactoryBean.setDurability(true);

		return jobDetailFactoryBean;
	}

	// 定义 抓取车评数据 触发器   任务二
	@Bean("crawlerAutohomeJobTrigger")
	public CronTriggerFactoryBean crawlerAutohomeJobTrigger(
			@Qualifier(value = "crawlerAutohomeJobBean") JobDetailFactoryBean itemJobBean) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(itemJobBean.getObject());
		tigger.setCronExpression("0/5 * * * * ? ");
		return tigger;
	}

	// 定义调度器
	@Bean
	public SchedulerFactoryBean schedulerFactory(CronTrigger[] cronTriggerImpl) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setTriggers(cronTriggerImpl);
		return bean;
	}
}
