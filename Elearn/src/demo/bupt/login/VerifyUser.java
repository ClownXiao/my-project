package demo.bupt.login;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import demo.bupt.metadata.CurrentUser;
import demo.bupt.metadata.User;
import demo.bupt.server.Server;

public class VerifyUser {

	private final static String URL_STR = Server.SERVER_URL + "VerifyUser";

	public static boolean check(String uname, String pwd) {
		boolean verified = false;
		try {
			URL url = new URL(URL_STR);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Charset", "utf-8");

			urlConn.connect();

			DataOutputStream dop = new DataOutputStream(
					urlConn.getOutputStream());
			dop.writeBytes("uname=" + URLEncoder.encode(uname, "utf-8"));
			dop.writeBytes("&pwd=" + URLEncoder.encode(pwd, "utf-8"));
			dop.flush();
			dop.close();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConn.getInputStream()));
			String result = "";
			String readLine = null;
			while ((readLine = bufferedReader.readLine()) != null) {
				/*
				 * 首行输入验证用户有效性 若验证有效，第二行接受用户数据
				 */
				result = readLine;
				if (result.equals("true")) {
					verified = true;
				} else if (result.equals("false")) {
					verified = false;
					return verified;
				} else {
					break;
				}
			}

			User user = new User();
			user.parseFromJson(result);
			CurrentUser.set(user);

			bufferedReader.close();
			urlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return verified;
	}
}