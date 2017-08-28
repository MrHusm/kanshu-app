package com.yxsd.kanshu.task;

import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 
 * @author qiong.wang
 *
 */
public class TaskFactory {

	private SchedulerFactoryBean schedulerFactoryBean;

	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";

	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	private static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

	private List<ScheduleJob> jobList;

	public void init() throws Exception {
		logger.info("任务启动");
		if (jobList != null && jobList.size() > 0) {
			for (ScheduleJob job : jobList) {
				logger.info("添加任务" + job.getJobName() + "启动");

				addJob(job);
				logger.info("添加任务" + job.getJobName() + "成功");

			}
		}
		logger.info("任务启动结束");

	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		try {
			JobDetail jobDetail = new JobDetail(job.getJobName(), JOB_GROUP_NAME, TaskJob.class);// 任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("url", job.getUrl());
			// 触发器
			CronTrigger trigger = new CronTrigger(job.getJobName(), TRIGGER_GROUP_NAME);// 触发器名,触发器组
			trigger.setCronExpression(job.getCronExpression());// 触发器时间设定
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			logger.error("添加任务启动失败", e);
			throw new RuntimeException(e);
		}
	}

	public List<ScheduleJob> getJobList() {
		return jobList;
	}

	public void setJobList(List<ScheduleJob> jobList) {
		this.jobList = jobList;
	}

}
