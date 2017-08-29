package com.yxsd.kanshu.task;

import com.yxsd.kanshu.base.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author qiong.wang
 *
 */
public class TaskJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(TaskJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始执行任务：" + arg0.getJobDetail().getName());
		String url = (String) arg0.getJobDetail().getJobDataMap().get("url");

		if (StringUtils.isBlank(url)) {
			logger.error("执行任务：" + arg0.getJobDetail().getName() + "失败");

		} else {
			HttpUtils.getContent(url);
		}

		logger.info("执行任务：" + arg0.getJobDetail().getName() + "完成");

	}
}