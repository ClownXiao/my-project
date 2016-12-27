package demo.bupt.metadata;

public class CurrentUser {

	private static User user = null;

	// private static boolean isAdmin = false;

	public static void set(User tmp) {
		user = tmp;
		// isAdmin = false;
	}

	public static User get() {
		return user;
	}

	/*
	 * public static void setAdmin(boolean i){ isAdmin = i; }
	 */

	/*
	 * public static boolean isAdmin(){ return isAdmin; }
	 */

	public static void destroy() {
		user = null;
		// isAdmin = false;
	}

	public static boolean isNull() {
		return (user == null);
	}
}