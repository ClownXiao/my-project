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
	private EditText zUser; // �ʺű༭��
	private EditText zPassword1,zPassword2; // ����༭��
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
							.setTitle("ע�����").setMessage("�˺Ż������벻��Ϊ�գ�\n���������룡")
							.create().show();
				} else if (!pwd1.equals(pwd2) ) {
					new AlertDialog.Builder(register.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("ע�����").setMessage("�����������벻һ�£�\n���������룡")
					.create().show();
				} else {
					new AlertDialog.Builder(register.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("ע��ɹ���").setMessage("��ϲ��ע��ɹ���")
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {


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
