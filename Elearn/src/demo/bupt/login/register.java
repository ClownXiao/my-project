package demo.bupt.login;

import demo.Elearn.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class register extends Activity {
	private EditText zUser; // 帐号编辑框
	private EditText zPassword1,zPassword2; // 密码编辑框
	Button register,reset;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		zUser = (EditText) findViewById(R.id.Zusername);
		zPassword1 = (EditText) findViewById(R.id.Zpassword1);
		zPassword2 = (EditText) findViewById(R.id.Zpassword2);
		register = (Button)findViewById(R.id.register_submit);
		reset = (Button)findViewById(R.id.reset);
		
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zUser.setText("");
				zPassword1.setText("");
				zPassword2.setText("");
			}
		});
	    
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname = zUser.getText().toString();
				String pwd1 = zPassword1.getText().toString();
				String pwd2 = zPassword2.getText().toString();
				if ("".equals(uname) || "".equals(pwd1)||"".equals(pwd2)) {
					new AlertDialog.Builder(register.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("注册错误").setMessage("账号或者密码不能为空，\n请重新输入！")
							.create().show();
				} else if (!pwd1.equals(pwd2) ) {
					new AlertDialog.Builder(register.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("注册错误").setMessage("两次密码输入不一致，\n请重新输入！")
					.create().show();
				} else {
					new AlertDialog.Builder(register.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("注册成功！").setMessage("恭喜您注册成功！")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {


								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
									
								}
								
							})
							.create().show();
				
			}
			}
		}
		);

}
}
