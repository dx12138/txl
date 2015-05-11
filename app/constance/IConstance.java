package constance;

import play.Play;

public class IConstance {

	public static String SEC_KEY = "";
	
	public static final int USER_UNLOCK = 0;
	
	public static final int USER_LOCKED = 1;
	
	public static final String ERROR = "error:";
	
	public static final long EXPIRES_IN = 86400000;
	
	static{
		SEC_KEY = Play.configuration.getProperty("application.secret").substring(0, 32);
	}
}
