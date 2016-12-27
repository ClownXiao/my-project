package demo.bupt.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import demo.Elearn.R;
import demo.bupt.Elearn;

public class LoadingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				/*
				 * Intent intent = new Intent
				 * (LoadingActivity.this,Whatsnew.class); startActivity(intent);
				 */
				LoadingActivity.this.finish();
				Toast.makeText(getApplicationContext(), "µÇÂ¼³É¹¦",
						Toast.LENGTH_SHORT).show();
			}
		}, 200);
		Intent intent = null;

		intent = new Intent(LoadingActivity.this, Elearn.class);
		startActivity(intent);
	}
}