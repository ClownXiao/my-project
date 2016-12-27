package demo.bupt.test;

import demo.Elearn.R;
import demo.bupt.common.intentBean;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class result extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_result);
		intentBean bean = (intentBean) getIntent().getExtras().getSerializable(
				"score");
		String answer[] = bean.ss;
		TextView output = (TextView) findViewById(R.id.test_result);
		output.setText(this.getStr(answer));
	}

	public String getStr(String[] args) {
		String str = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				str += (String) args[i];
			}
		}
		return str;
	}

}
