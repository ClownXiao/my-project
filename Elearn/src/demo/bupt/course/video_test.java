package demo.bupt.course;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import demo.Elearn.R;
import demo.bupt.test.TestsExample;

public class video_test extends Activity {

	int tid = 1;
	String answer[] = new String[20];
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
		setContentView(R.layout.video_test);

		testNum = (TextView) findViewById(R.id.VtestNum);
		testTitle = (TextView) findViewById(R.id.VtestTitle);
		
		// 提交答卷
		submit = (Button) findViewById(R.id.Vbuttonsubmit);

		group = (RadioGroup) findViewById(R.id.VradioGroupAnswer);
		selectA = (RadioButton) findViewById(R.id.Vradio0);
		selectB = (RadioButton) findViewById(R.id.Vradio1);
		selectC = (RadioButton) findViewById(R.id.Vradio2);
		selectD = (RadioButton) findViewById(R.id.Vradio3);

		testNum.setText("题目" + tests1.get(0).getTestNum() + "：");
		testTitle.setText("系统执行器如何生成的");
		selectA.setText("动态的");
		selectB.setText("静态的");
		selectC.setText("随机的");
		selectD.setText("用户定义的");
		
		final AlertDialog.Builder ask = new AlertDialog.Builder(this);
		 ask.setTitle("请输入") 
		 .setIcon(android.R.drawable.ic_dialog_info)  
		 .setView(new EditText(this))  
		 .setPositiveButton("确定", null)  
		 .setNegativeButton("取消", null);

		
		
		
		submit.setOnClickListener(new ButtonSubmit());
	
	
	

		
	}
	
	class ButtonSubmit implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(selectA.isChecked()){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						video_test.this);
				builder1.setTitle("提示：");
				builder1.setMessage("恭喜你回答正确！！");
				builder1.setPositiveButton("返回", 
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();

					}
				});
				builder1.show();
			}
			else{
				AlertDialog.Builder builder2 = new AlertDialog.Builder(
						video_test.this);
				builder2.setTitle("提示：");
				builder2.setMessage("选错啦！有什么不懂得？\n需要提问么");
				builder2.setPositiveButton("需要",new askclick());
				builder2.setNegativeButton("不需要", 
		                 new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();

					}
				});
				builder2.show();
			}
		}
	}//class button
	
	class askclick implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			AlertDialog.Builder ask = new AlertDialog.Builder(video_test.this);
			 ask.setTitle("请输入") 
			 .setIcon(android.R.drawable.ic_dialog_info)  
			 .setView(new EditText(video_test.this))  
			 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				 @Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();

					}
			 })
			 .setNegativeButton("取消", null);
			ask.show();
		}
	}
}
