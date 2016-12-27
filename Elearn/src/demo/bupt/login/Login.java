package demo.bupt.login;

import demo.Elearn.R;
import demo.bupt.course.CourseDetail;
import demo.bupt.course.VideoPlay;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import demo.bupt.Elearn;

public class Login extends Activity {
	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
	Button blogin,bregister;
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		 
		        super.onStart();  
		        //checkNetWork();  
		  
		   
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.username);
		mPassword = (EditText) findViewById(R.id.password);
		blogin = (Button)findViewById(R.id.login_submit);
		bregister = (Button)findViewById(R.id.login_reset);
		
		bregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = null;

				intent = new Intent(Login.this, register.class);
				startActivity(intent);
			}
		});

		blogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname = mUser.getText().toString();
				String pwd = mPassword.getText().toString();
				if ("".equals(uname) || "".equals(pwd)) {
					new AlertDialog.Builder(Login.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("��¼����").setMessage("�˺Ż������벻��Ϊ�գ�\n��������ٵ�¼��")
							.create().show();
				} else if ("Mooc".equals(uname) && "123456".equals(pwd)) {
					Intent intent = new Intent();
					intent.setClass(Login.this, LoadingActivity.class);
					startActivity(intent);
					finish();
					// Toast.makeText(getApplicationContext(), "��¼�ɹ�",
					// Toast.LENGTH_SHORT).show();
				} else {
					new AlertDialog.Builder(Login.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("��¼ʧ��").setMessage("�ʺŻ������벻��ȷ��\n������������룡")
							.create().show();
				
			}
			}
		}
		);
		

	}
	
	

	public void login_main(View v) {
		String uname = mUser.getText().toString();
		String pwd = mPassword.getText().toString();
		if ("".equals(uname) || "".equals(pwd)) {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼����").setMessage("�˺Ż������벻��Ϊ�գ�\n��������ٵ�¼��")
					.create().show();
		} else if (VerifyUser.check(uname, pwd)) {
			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			startActivity(intent);
			// Toast.makeText(getApplicationContext(), "��¼�ɹ�",
			// Toast.LENGTH_SHORT).show();
		} else {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼ʧ��").setMessage("�ʺŻ������벻��ȷ��\n������������룡")
					.create().show();
		}

		// ��¼��ť
		/*
		 * Intent intent = new Intent();
		 * intent.setClass(Login.this,Whatsnew.class); startActivity(intent);
		 * Toast.makeText(getApplicationContext(), "��¼�ɹ�",
		 * Toast.LENGTH_SHORT).show(); this.finish();
		 */
	}
	

	
	
	public void login_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

	public void login_pw(View v) { // �������밴ť
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}
}
