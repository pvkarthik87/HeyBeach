package com.karcompany.heybeach.config;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 * Main constants file entire application.
 * Any app related constants should be added here.
 */

public class Constants {

	public static final String SERVER_BASE_URL = "http://139.59.158.8:3000/";

	public static final int CONN_READ_TIMEOUT = 15000; // 15 secs
	public static final int CONN_TIMEOUT = 15000; // 15 secs

	public static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";

	public enum TabType {
		LOGIN,
		SINUP
	}

	public interface Tab {

		TabType getType();

	}

	public static class LoginTab implements Tab {

		@Override
		public TabType getType() {
			return TabType.LOGIN;
		}
	}

	public static class SignUpTab implements Tab {

		@Override
		public TabType getType() {
			return TabType.SINUP;
		}
	}

}
