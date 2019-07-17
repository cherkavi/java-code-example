package com.api_impl;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.api.IMessage;

public class MessageImpl implements IMessage{
	@Override
	public String getMessage() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return "This is test Service "+sdf.format(new Date());
	}

}
