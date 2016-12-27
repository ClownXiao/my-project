package demo.bupt.test;

//import demo.Elearn.R;
import java.util.ArrayList;
import java.util.List;

import demo.bupt.Act_test;
import demo.bupt.Elearn;
import demo.bupt.Test;
import demo.bupt.common.intentBean;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import demo.Elearn.R;

public class testActivity extends Activity {

	int tid = 1;
	String answer[] = new String[20];
	Button nextButton = null;
	Button lastButton = null;
	// TextView stem=null;
	RadioGroup group = null;
	RadioButton selectA, selectB, selectC, selectD;
	Button submit = null;

	TextView testNum = null;
	TextView testTitle = null;
	TestsExample tests1 = new TestsExample();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		testNum = (TextView) findViewById(R.id.testNum);
		testTitle = (TextView) findViewById(R.id.testTitle);
		// 上一题
		nextButton = (Button) findViewById(R.id.buttonnext);
		// 下一题
		lastButton = (Button) findViewById(R.id.buttonlast);
		// 提交答卷
		submit = (Button) findViewById(R.id.buttonsubmit);

		group = (RadioGroup) findViewById(R.id.radioGroupAnswer);
		selectA = (RadioButton) findViewById(R.id.radio0);
		selectB = (RadioButton) findViewById(R.id.radio1);
		selectC = (RadioButton) findViewById(R.id.radio2);
		selectD = (RadioButton) findViewById(R.id.radio3);

		testNum.setText("题目" + tests1.get(0).getTestNum() + "：");
		testTitle.setText(tests1.get(0).getTestTitle());
		selectA.setText(tests1.get(0).getAnswerA());
		selectB.setText(tests1.get(0).getAnswerB());
		selectC.setText(tests1.get(0).getAnswerC());
		selectD.setText(tests1.get(0).getAnswerD());

		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == selectA.getId()) {
					answer[tid] = "A";

				} else if (checkedId == selectB.getId()) {
					answer[tid] = "B";

				} else if (checkedId == selectC.getId()) {
					answer[tid] = "C";

				} else if (checkedId == selectD.getId()) {
					answer[tid] = "D";

				}

			}
		});

		nextButton.setOnClickListener(new ButtonListenerNext());
		lastButton.setOnClickListener(new ButtonListenerLast());
		submit.setOnClickListener(new ButtonSubmit());
	}

	// 交卷
	class ButtonSubmit implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			AlertDialog.Builder builder2 = new AlertDialog.Builder(
					testActivity.this);
			builder2.setTitle("提示：");
			builder2.setMessage("恭喜您胶卷成功哦！快去看看你的成绩吧！");

			// 查看测试结果
			builder2.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							intentBean bean = new intentBean();
							bean.ss = answer;

							
							Intent intent = new Intent(testActivity.this,
									result.class);
							Bundle bu = new Bundle();
							bu.putSerializable("score", bean);
							intent.putExtras(bu);
							startActivity(intent);

						}
					});
			builder2.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							Intent intent = new Intent(testActivity.this,
									Elearn.class);
							startActivity(intent);

						}
					});
			builder2.create().show();
			// Intent intent = new Intent(testActivity.this, result.class);
			// Bundle bu = new Bundle();
			// bu.putSerializable("score", bean);
			// intent.putExtras(bu);
			// startActivity(intent);
		}

	}

	class ButtonListenerNext implements OnClickListener {
		@Override
		public void onClick(View v) {

			if (tid < 5) {
				tid++;
				testNum.setText("题目" + tid + "：");

				testTitle.setText(tests1.get(tid - 1).getTestTitle());
				selectA.setText(tests1.get(tid - 1).getAnswerA());
				selectB.setText(tests1.get(tid - 1).getAnswerB());
				selectC.setText(tests1.get(tid - 1).getAnswerC());
				selectD.setText(tests1.get(tid - 1).getAnswerD());
				if (answer[tid] != null) {
					if (answer[tid].equals("A")) {
						selectA.setChecked(true);
					} else if (answer[tid].equals("B")) {
						selectB.setChecked(true);
					} else if (answer[tid].equals("C")) {
						selectC.setChecked(true);
					} else if (answer[tid].equals("D")) {
						selectD.setChecked(true);
					}
				} else {
					selectA.setChecked(false);
					selectB.setChecked(false);
					selectC.setChecked(false);
					selectD.setChecked(false);
				}

			} else {
				// 已经为最后一题！
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						testActivity.this);
				builder1.setTitle("提示");
				builder1.setMessage("已经为最后一题！");
				builder1.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder1.create().show();
			}
		}

	}

	class ButtonListenerLast implements OnClickListener {
		@Override
		public void onClick(View v) {

			if (tid > 1) {

				tid--;

				testNum.setText("题目" + tid + "：");

				testTitle.setText(tests1.get(tid - 1).getTestTitle());
				selectA.setText(tests1.get(tid - 1).getAnswerA());
				selectB.setText(tests1.get(tid - 1).getAnswerB());
				selectC.setText(tests1.get(tid - 1).getAnswerC());
				selectD.setText(tests1.get(tid - 1).getAnswerD());
				if (answer[tid] != null) {

					if (answer[tid].equals("A")) {
						selectA.setChecked(true);
					} else if (answer[tid].equals("B")) {
						selectB.setChecked(true);
					} else if (answer[tid].equals("C")) {
						selectC.setChecked(true);
					} else if (answer[tid].equals("D")) {
						selectD.setChecked(true);
					}
				} else {
					selectA.setChecked(false);
					selectB.setChecked(false);
					selectC.setChecked(false);
					selectD.setChecked(false);
				}
			} else {
				// 已经是最后一题，弹出警告对话框
				AlertDialog.Builder builder = new AlertDialog.Builder(
						testActivity.this);
				builder.setTitle("提示：");
				builder.setMessage("已经为第一题");
				builder.setPositiveButton("確定",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.create().show();

			}
		}

	}

}
