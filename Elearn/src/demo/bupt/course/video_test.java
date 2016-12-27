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
		
		// �ύ���
		submit = (Button) findViewById(R.id.Vbuttonsubmit);

		group = (RadioGroup) findViewById(R.id.VradioGroupAnswer);
		selectA = (RadioButton) findViewById(R.id.Vradio0);
		selectB = (RadioButton) findViewById(R.id.Vradio1);
		selectC = (RadioButton) findViewById(R.id.Vradio2);
		selectD = (RadioButton) findViewById(R.id.Vradio3);

		testNum.setText("��Ŀ" + tests1.get(0).getTestNum() + "��");
		testTitle.setText("ϵͳִ����������ɵ�");
		selectA.setText("��̬��");
		selectB.setText("��̬��");
		selectC.setText("�����");
		selectD.setText("�û������");
		
		final AlertDialog.Builder ask = new AlertDialog.Builder(this);
		 ask.setTitle("������") 
		 .setIcon(android.R.drawable.ic_dialog_info)  
		 .setView(new EditText(this))  
		 .setPositiveButton("ȷ��", null)  
		 .setNegativeButton("ȡ��", null);

		
		
		
		submit.setOnClickListener(new ButtonSubmit());
	
	
	

		
	}
	
	class ButtonSubmit implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(selectA.isChecked()){
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						video_test.this);
				builder1.setTitle("��ʾ��");
				builder1.setMessage("��ϲ��ش���ȷ����");
				builder1.setPositiveButton("����", 
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
				builder2.setTitle("��ʾ��");
				builder2.setMessage("ѡ��������ʲô�����ã�\n��Ҫ����ô");
				builder2.setPositiveButton("��Ҫ",new askclick());
				builder2.setNegativeButton("����Ҫ", 
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
			 ask.setTitle("������") 
			 .setIcon(android.R.drawable.ic_dialog_info)  
			 .setView(new EditText(video_test.this))  
			 .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				 @Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();

					}
			 })
			 .setNegativeButton("ȡ��", null);
			ask.show();
		}
	}
}
