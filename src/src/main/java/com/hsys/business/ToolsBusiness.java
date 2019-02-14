package com.hsys.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hsys.business.beans.LoadInitialDataBean;
import com.hsys.common.HsysString;
import com.hsys.io.InitialDataTxtDataReader;
import com.hsys.models.UserModel;
import com.hsys.security.HsysPasswordEncoder;
import com.hsys.services.UserService;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/02/08
 */
@Component
public class ToolsBusiness {
	@Autowired
    private UserService userService;
	@Autowired
	private InitialDataTxtDataReader reader;
	@Autowired
	private HsysPasswordEncoder encoder;
	
	@Transactional
	public void loadInitialData(LoadInitialDataBean bean) {
		String userFile = bean.getUserFilePath();
		if(!HsysString.isNullOrEmpty(userFile)) {
			loadUsers(userFile);
		}
	}

	@Transactional
	private void loadUsers(String userFile) {
		List<UserModel> users = reader.readUsers(userFile);
		for(UserModel u : users) {
			UserModel user = userService.queryByNo(u.getNo());
			if(user == null) {
				userService.add(u);
				
				u.setPassword(encoder.encode("123"));
				u.setUpdate(UserModel.FIELD_PASSWORD);//init pwd
				userService.update(u);
			} else {
				u.setId(user.getId());
				
				u.setUpdate(UserModel.FIELD_NAME);
				u.setUpdate(UserModel.FIELD_SEX);
				u.setUpdate(UserModel.FIELD_MAIL);
				u.setUpdate(UserModel.FIELD_PLACE);
				u.setUpdate(UserModel.FIELD_MAJOR);
				u.setUpdate(UserModel.FIELD_ADDRESS);
				u.setUpdate(UserModel.FIELD_ID_NUMBER);
				u.setUpdate(UserModel.FIELD_SCHOOL);
				u.setUpdate(UserModel.FIELD_DEGREE);
				u.setUpdate(UserModel.FIELD_GRADUATE_DATE);
				u.setUpdate(UserModel.FIELD_ENTER_DATE);
				u.setUpdate(UserModel.FIELD_EXIT_DATE);
				
				userService.update(u);
			}
		}
	}
}
