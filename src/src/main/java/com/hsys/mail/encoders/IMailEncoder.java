package com.hsys.mail.encoders;

import com.hsys.services.beans.MineMail;

public interface IMailEncoder {
	void pre(String name, Object[] args);
	void post();
	MineMail encode();
}
