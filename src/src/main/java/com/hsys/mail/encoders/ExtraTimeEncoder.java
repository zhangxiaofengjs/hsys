package com.hsys.mail.encoders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.common.HsysDate;
import com.hsys.config.MailConfig;
import com.hsys.models.ExtraTimeModel;
import com.hsys.services.ExtraTimeService;
import com.hsys.services.beans.MineMail;

@Component
public class ExtraTimeEncoder implements IMailEncoder {
	@Autowired
	MailConfig mailConfig;
	@Autowired
	ExtraTimeService extraTimeService;
	
	private String name = null;
	private Object[] args = null;
	private ExtraTimeModel extraTime = null;
	
	@Override
	public void pre(String name, Object[] args) {
		this.name = name;
		this.args = args;
		
		if("update".equals(this.name))
		{
			ExtraTimeModel et = (ExtraTimeModel)args[0];
			this.extraTime = extraTimeService.queryById(et.getId());
		}
		else if("delete".equals(this.name)) {
			this.extraTime = extraTimeService.queryById((Integer)args[0]);
		}
	}

	@Override
	public void post() {
	}
	
	@Override
	public MineMail encode() {
		if(args == null || args.length != 1) {
			return null;
		}
		
		MineMail mm = new MineMail();
		mm.setFrom(mailConfig.getFrom());
		mm.setTo(mailConfig.getTo());
		
		StringBuilder sb = new StringBuilder();
		
		if("add".equals(this.name)) {
			ExtraTimeModel et = (ExtraTimeModel)args[0];
			mm.setSubject(
				String.format("[加班记录][添加]%s, %s, %.2fh",
					et.getUser().getName(),
					HsysDate.format(et.getDate()),
					et.getLength()));
			sb.append(String.format("<b>添加</b><br>"));
			sb.append(encodeExtraTime(et));
		} else if("update".equals(this.name)) {
			ExtraTimeModel et = (ExtraTimeModel)args[0];
			mm.setSubject(
				String.format("[加班记录][更新]%s, %s, %.2fh",
					et.getUser().getName(),
					HsysDate.format(et.getDate()),
					et.getLength()));
			sb.append(String.format("<b>更新</b><br>"));
			sb.append("变更前:" + encodeExtraTime(extraTime));
			sb.append("<br>");
			sb.append("变更后:" + encodeExtraTime(et));
		} else {
			mm.setSubject(
				String.format("[加班记录][删除]%s, %s, %.2fh",
					extraTime.getUser().getName(),
					HsysDate.format(extraTime.getDate()),
					extraTime.getLength()));
			sb.append(String.format("<b>删除</b><br>"));
			sb.append(encodeExtraTime(extraTime));
		}

	    mm.setMessage(sb.toString());
		return mm;
	}
	
	private String encodeExtraTime(ExtraTimeModel et) {
		String body = String.format("[%d]%s, %s, %s, %s, %.2f小时, 午餐:%s, 晚餐:%s",
				et.getUser().getId(),
				et.getUser().getName(),
				HsysDate.format(et.getDate()),
				HsysDate.format(et.getStartTime(), "HH:mm"),
				HsysDate.format(et.getEndTime(), "HH:mm"),
				et.getLength(),
				et.getMealLunch() == 0 ?"否":"是",
				et.getMealSupper()== 0 ?"否":"是");
		return body;
	}
}
