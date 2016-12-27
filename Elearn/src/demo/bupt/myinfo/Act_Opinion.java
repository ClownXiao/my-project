package demo.bupt.myinfo;

import demo.Elearn.R;
import demo.bupt.Act_myInfo;
import demo.bupt.myinfo.Act_Opinion;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

/**
 * @author fei 意见反馈
 */
public class Act_Opinion extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo_opinion);
		setTitle("意见反馈");

		EditText myOpinion = (EditText) findViewById(R.id.myinfo_opinion);

		Button submitOpinion = (Button) findViewById(R.id.submit_useropinion);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 提交用户意见
		submitOpinion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder.setTitle("提示");
				builder.setMessage("您的意见已提交，非常感谢！");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							// 意见提交后，页面转入我的信息页面
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// InputMethodManager imm = (InputMethodManager)
								// getSystemService(Context.INPUT_METHOD_SERVICE);
								// //
								// imm.hideSoftInputFromInputMethod(v.getWindowToken(),
								// // 0);
								// imm.toggleSoftInput(0,
								// InputMethodManager.HIDE_NOT_ALWAYS);
								Intent intent = new Intent(Act_Opinion.this,
										Act_myInfo.class);
								startActivity(intent);

							}
						});
				// 创建、并显示对话框
				builder.create().show();

			}
		});

	}

}
