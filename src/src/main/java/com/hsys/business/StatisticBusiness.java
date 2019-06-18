package com.hsys.business;




import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hsys.business.forms.StatisticHtmlForm;
import com.hsys.common.HsysList;
import com.hsys.models.StatisticModel;
import com.hsys.services.StatisticService;

/**
 * @author: zhangyanhong@hyron.com
 * @version: 2019/04/02
 */

@Component
public class StatisticBusiness {
	@Autowired
	private StatisticService statisticService;

	public List<StatisticModel> getListStatistics(StatisticHtmlForm form) {		
		List<StatisticModel> listRet = null ;
		
		StatisticModel statisticModel = new StatisticModel();
		statisticModel.setCond(StatisticModel.COND_START_DATE, form.getStart());
		statisticModel.setCond(StatisticModel.COND_END_DATE, form.getEnd());
		statisticModel.setCond(StatisticModel.COND_DATE_START, form.getStart());
		statisticModel.setCond(StatisticModel.COND_DATE_END, form.getEnd());
		
		
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
	

	
	
}
