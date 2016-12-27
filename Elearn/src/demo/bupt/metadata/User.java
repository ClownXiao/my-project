package demo.bupt.metadata;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String id = "";
	private String uname = "";
	private String pwd = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void parseFromJson(String json) {

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			this.id = jsonObject.getString("id").toString();
			this.uname = jsonObject.get("uname").toString();
			this.pwd = jsonObject.get("pwd").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String parseToJson() {

		String json = "";
		Map<String, String> map = new HashMap<String, String>();

		map.put("id", this.id);
		map.put("uname", this.uname);
		map.put("pwd", this.pwd);

		JSONObject jsonObject = new JSONObject(map);
		json = jsonObject.toString();

		return json;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", pwd=" + pwd + "]";
	}

}
