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
	public int nowPage = 1;// ��ǰ�ڼ�ҳ
	public int pageSize = 15;// ÿҳ����
	private PullToRefreshListView weibolist;// ΢����Ϣ��ʾ
	private View loginprogress;// ��ʼҳ������
	private LinearLayout moreweibo;// �ײ�������
	private ProgressBar progressBar;// �ײ�������Ľ�����
	private ProgressBar titleprogressBar;// ����������
	private WeiboAdapter adapter;// ΢����Ϣ��������
	private ImageView btrefaush;// ˢ��΢���İ�ť

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
		// �ײ�����
		View bottom = LayoutInflater.from(this).inflate(R.layout.itembottom,
				null);
		View title = this.findViewById(R.id.freelook_title_home);
		// ����ͷ�Ĳ���
		titleprogressBar = (ProgressBar) title
				.findViewById(R.id.titleprogressBar);
		// �м���ʾ�ǳ�
		TextView tvname = (TextView) title.findViewById(R.id.tvinfo);

		tvname.setText("���ϰ�");
		// д����Ϣ��ť
		ImageView btwriteWeibo = (ImageView) title
				.findViewById(R.id.title_bt_left);
		btwriteWeibo.setImageResource(R.drawable.ic_btn_post_text);
		// ˢ�°�ť
		btrefaush = (ImageView) title.findViewById(R.id.title_bt_right);
		btrefaush.setImageResource(R.drawable.ic_btn_refresh);
		// �ײ�������
		progressBar = (ProgressBar) bottom.findViewById(R.id.progressBar);
		// ��bottom���ӵ�ListView�еĵײ�
		weibolist.addFooterView(bottom);
		moreweibo = (LinearLayout) bottom.findViewById(R.id.moreweibo);
		loginprogress = this.findViewById(R.id.loginprogres);
		// ����ײ�����Ĳ���

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

}