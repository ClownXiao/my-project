package demo.bupt;

import java.util.ArrayList;
import java.util.List;

import demo.Elearn.R;
import demo.bupt.contact.ContactActivity;
import demo.bupt.myinfo.Act_MyFavorite;
import demo.bupt.myinfo.Act_Notice;
import demo.bupt.myinfo.Act_Opinion;
import demo.bupt.myinfo.Act_Pay;
import demo.bupt.myinfo.Act_Rank;
import demo.bupt.myinfo.Act_StudyRecord;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

public class Act_myInfo extends Activity {
	private List<String> data = null;
	private ListView listview;
	private TextView photo, name, nameInfo;

	public List<String> getDataSource() {
		List<String> list = new ArrayList<String>();
		list.add("我的学习记录");
		list.add("我的收藏夹");
		list.add("我的通讯录");
		list.add("等级积分");
		list.add("激活充值");
		list.add("公告栏");
		list.add("意见反馈");
		list.add("系统版本使用说明");
		return list;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);

		data = this.getDataSource();

		BaseAdapter baseAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub

				if (position == 0) {
					View view = View.inflate(Act_myInfo.this,
							R.layout.photoitem, null);
					photo = (TextView) view.findViewById(R.id.myPhoto);
					photo.setTextSize(30);
					return view;
				}
				if (position == 1) {
					View view = View.inflate(Act_myInfo.this,
							R.layout.nameitem, null);
					name = (TextView) view.findViewById(R.id.myName);
					name.setTextSize(30);
					nameInfo = (TextView) view.findViewById(R.id.myNameInfo);
					nameInfo.setTextSize(30);
					return view;
				} else {
					TextView tv = new TextView(Act_myInfo.this);
					tv.setText(data.get(position - 2));
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(30);
					return tv;
				}

			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return data.size() + 2;
			}
		};

		listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(baseAdapter);

		final Builder builder = new AlertDialog.Builder(this);

		// 为每个列表项添加点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 我的学习记录
				if (position == 2) {
					System.out.println(position);
					Intent intent = new Intent(Act_myInfo.this,
							Act_StudyRecord.class);
					startActivity(intent);
				}
				// 我的收藏夹
				if (position == 3) {
					System.out.println(position);
					Intent intent = new Intent(Act_myInfo.this,
							Act_MyFavorite.class);
					startActivity(intent);
				}
				// 我的通讯录
				/*
				 * if (position == 4) { System.out.println(position); Intent
				 * intent = new Intent(Act_myInfo.this, Act_StudyRecord.class);
				 * startActivity(intent); }
				 */
				if (position == 4) {
					System.out.println(position);
					Intent intent = new Intent(Act_myInfo.this,
							ContactActivity.class);
					startActivity(intent);
				}
				// 等级积分
				if (position == 5) {
					Intent intent = new Intent(Act_myInfo.this, Act_Rank.class);
					startActivity(intent);
				}
				// 激活充值
				if (position == 6) {
					Intent intent = new Intent(Act_myInfo.this, Act_Pay.class);
					startActivity(intent);
				}
				// 公告栏
				if (position == 7) {
					Intent intent = new Intent(Act_myInfo.this,
							Act_Notice.class);
					startActivity(intent);
				}
				// 意见反馈
				if (position == 8) {
					Intent intent = new Intent(Act_myInfo.this,
							Act_Opinion.class);
					startActivity(intent);
				}
				// 版本说明
				if (position == 9) {

					// 设置对话框的图标
					// builder.setIcon(R.drawable.tools);
					// 设置对话框的标题
					builder.setTitle("关于");
					// 设置对话框显示的内容
					builder.setMessage("销售学习宝     版本：1.0");

					// 为对话框设置一个“确定”按钮
					builder.setPositiveButton("确定"
					// 为列表项的单击事件设置监听器
							, new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
								}
							});

					// 创建、并显示对话框
					builder.create().show();
				}
			}

		});
		builder.create().show();

	}

}
