package com.dwu.alonealong.controller;

import java.io.Serializable;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.exception.NotLoginException;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class UserSession implements Serializable {

	private User user;

	public UserSession(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public static String getUserId(HttpServletRequest request) throws Exception {
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if(userSession == null) {
			throw new NotLoginException();
		}
		return userSession.getUser().getId();
	}
}
