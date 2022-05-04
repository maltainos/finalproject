package mz.gov.misau.sigsmi.ws.security;

import mz.gov.misau.sigsmi.ws.SpringApplicationContext;

public class SecurityConstants {

	public static final long PASSWORD_RESET_EXPIRATION_TIME = 1000 * 60 * 60;// 1hour
	public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24;// 1 day
	public static final long REFRESH_EXPIRATION_TIME = 864000000;// 10 dias em millesecundos
	public static final String SIGN_UP_URL = "/users";
	public static final String DOCS_UP_URL = "/ 	 ";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER_STRING = "Authorization";
	public static final String REFRESH_TOKEN_HEADER_STRING = "Authorization-Refresh";
	public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
	public static final String PASSWORD_RESET_URL = "/users/password-reset-request";

	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

	public static String getAccessKey() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getAccessKey();
	}

	public static String getSecretKey() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getSecretKey();
	}

}