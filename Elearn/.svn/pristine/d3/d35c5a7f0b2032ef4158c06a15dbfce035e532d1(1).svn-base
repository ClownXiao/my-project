package demo.bupt.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import demo.Elearn.R;
import demo.bupt.test.testActivity;

public class CourseDetail extends Activity {

	TextView title;
	String text, course, chapter;
	Button video_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.course_detail);

		title = (TextView) findViewById(R.id.course_name);
		video_button = (Button) findViewById(R.id.video_button);
		course = getIntent().getStringExtra("course");
		chapter = getIntent().getStringExtra("chapter");
		text = getIntent().getStringExtra("course")
				+ getIntent().getStringExtra("chapter");
		title.setText(text);
		video_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = null;

				intent = new Intent(CourseDetail.this, VideoPlay.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.course_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		final Builder builder = new AlertDialog.Builder(this);
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.evaluate:
			builder.setTitle("请输入评价");
			LinearLayout evaluate = (LinearLayout) getLayoutInflater().inflate(
					R.layout.evaluate, null);
			builder.setView(evaluate);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.create().show();
			break;
		case R.id.question:
			builder.setTitle("请输入问题");
			LinearLayout question = (LinearLayout) getLayoutInflater().inflate(
					R.layout.question, null);
			builder.setView(question);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			builder.create().show();
			break;
		case R.id.share:
			break;
		case R.id.test:
			Intent intent = new Intent(CourseDetail.this, testActivity.class);
			intent.putExtra("course", course);
			intent.putExtra("course", chapter);
			startActivity(intent);
			break;
		default:
			return false;
		}
		return true;
	}

}
