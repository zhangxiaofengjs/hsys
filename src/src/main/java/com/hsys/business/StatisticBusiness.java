package com.hsys.business;




import java.io.IOException;

import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hsys.HsysApplicationContext;
import com.hsys.business.forms.StatisticDownloadForm;
import com.hsys.business.forms.StatisticHtmlForm;
import com.hsys.common.HsysIO;
import com.hsys.common.HsysList;
import com.hsys.config.UploadFolderConfig;
import com.hsys.io.StatisticWriter;
import com.hsys.models.GroupModel;
import com.hsys.models.StatisticModel;
import com.hsys.services.GroupService;
import com.hsys.services.StatisticService;

/**
 * @author: zhangyanhong@hyron.com
 * @version: 2019/04/02
 */

@Component
public class StatisticBusiness {
	@Autowired
	private StatisticService statisticService;
	@Autowired
	private GroupService groupService;
	@Autowired
	UploadFolderConfig config;
	@Autowired
	private  StatisticWriter writer;
	
	public List<StatisticModel> getListStatistics(StatisticHtmlForm form) {		
		List<StatisticModel> listRet = null ;
		
		StatisticModel statisticModel = new StatisticModel();
		statisticModel.setCond(StatisticModel.COND_START_DATE, form.getStart());
		statisticModel.setCond(StatisticModel.COND_END_DATE, form.getEnd());
		statisticModel.setCond(StatisticModel.COND_DATE_START, form.getStart());
		statisticModel.setCond(StatisticModel.COND_DATE_END, form.getEnd());
		
		if(form.getGroupId()!=0) {
			List<Integer> groupIds = groupService.queryChildrenIdsById(form.getGroupId());
			groupIds.add(form.getGroupId());
			statisticModel.setCond(StatisticModel.COND_GROUP_IDS, groupIds);
			//页面需要显示名字，form再设定
			GroupModel group = groupService.queryById(form.getGroupId());
			if(group != null) {
				form.setGroupName(group.getName());
			}
		}
		
		
		if (form.getApproveType() != 0) {
			statisticModel.setCond(StatisticModel.COND_STATUS, 1);
		}
		if(form.getSumType() == 0) {   //'0'按组查询
			statisticModel.setCond(StatisticModel.COND_SUM_TYPE_NULL, true);
			listRet = statisticService.GetGroupStatisticsList(statisticModel);
		} else if(form.getSumType() == 1) { //'1'按人员查询
			
			statisticModel.setCond(StatisticModel.COND_SUM_TYPE_NOT_NULL, true);
			listRet = statisticService.queryList(statisticModel);
		}else {
			HsysList.New();
		}
		
		List<StatisticModel>  listTotal = statisticService.SumTotalList(statisticModel);
		listRet.addAll(listTotal);
		return listRet;
	}

	public ResponseEntity<byte[]> download(StatisticDownloadForm form) throws IOException {
		Resource resource = HsysApplicationContext.getResource("classpath:/attachments/attendance-statistic-template.xlsx"); 
		InputStream is = resource.getInputStream();

		String tempFile = config.getTempFolder() + "\\" + "统计表.xlsx";	//生成文件名

 		writer.setStart(form.getStartDate());
		writer.setEnd(form.getEndDate());
		writer.setApproveType(form.getApproveType());
		writer.setGroupId(form.getParent().getId());
		writer.setSumType(form.getSumType());
		writer.setTemplateFileStream(is);
		writer.write(tempFile);
		is.close();	
		ResponseEntity<byte[]> response = HsysIO.downloadHttpFile(tempFile);
		HsysIO.delete(tempFile);
		return response;
		
	}
	

	
	
}
