package demo.bupt.communication;

import demo.bupt.communication.PullToRefreshListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import demo.Elearn.R;
import demo.bupt.logic.IElearnActivity;

public class FHomeActivity extends Activity implements IElearnActivity {
	public static final int REFRESH_WEIBO = 1;
	public int nowPage = 1;// 当前第几页
	public int pageSize = 15;// 每页条数
	private PullToRefreshListView weibolist;// 微博信息显示
	private View loginprogress;// 开始页进度条
	private LinearLayout moreweibo;// 底部更多项
	private ProgressBar progressBar;// 底部更多项的进度条
	private ProgressBar titleprogressBar;// 顶部进度条
	private WeiboAdapter adapter;// 微博信息的适配器
	private ImageView btrefaush;// 刷新微博的按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_home);
		initView();
		// MainService.allActivity.add(this);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		weibolist = (PullToRefreshListView) this.findViewById(R.id.weibolist);
		// 底部更多
		View bottom = LayoutInflater.from(this).inflate(R.layout.itembottom,
				null);
		View title = this.findViewById(R.id.freelook_title_home);
		// 标题头的布局
		titleprogressBar = (ProgressBar) title
				.findViewById(R.id.titleprogressBar);
		// 中间显示昵称
		TextView tvname = (TextView) title.findViewById(R.id.tvinfo);

		tvname.setText("孔老板");
		// 写新消息按钮
		ImageView btwriteWeibo = (ImageView) title
				.findViewById(R.id.title_bt_left);
		btwriteWeibo.setImageResource(R.drawable.ic_btn_post_text);
		// 刷新按钮
		btrefaush = (ImageView) title.findViewById(R.id.title_bt_right);
		btrefaush.setImageResource(R.drawable.ic_btn_refresh);
		// 底部进度条
		progressBar = (ProgressBar) bottom.findViewById(R.id.progressBar);
		// 将bottom添加到ListView中的底部
		weibolist.addFooterView(bottom);
		moreweibo = (LinearLayout) bottom.findViewById(R.id.moreweibo);
		loginprogress = this.findViewById(R.id.loginprogres);
		// 点击底部更多的布局

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

}
