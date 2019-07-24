package com.hsys.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsys.schedule.jobs.DataBaseBackupJob;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/07/23
 */
@Service
public class JobShop {
	 @Autowired
	 private Scheduler scheduler;
	
	public void initialize() {
		//备份数据库
		createJob(DataBaseBackupJob.class,
				  "hsys",
				  "0 0 22 0 0 5",//每周五22:00执行
				  "数据库备份计划Job",
				  "数据库备份计划JobTrigger");
	}
	
	private void createJob(Class<? extends Job> jobClass, String jobGroup, String cronExpression, String jobDescription, String triggerDescription) {
		try {
			String jobName = jobClass.getName();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).withDescription(jobDescription).build();
			
			//create a trigger
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(triggerDescription).withSchedule(schedBuilder).build();
			
			//scheduled!!
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			System.err.println(e);
		}
	}
}
