package demo.bupt.groupchat;


import java.util.ArrayList;




import demo.Elearn.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class GHomeActivity extends Activity {
	private ImageView set;
	private ImageView add;
	private ListView grouplistview;
	SelectAddPopupWindow menuWindow2; //弹出框
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g_home);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		grouplistview = (ListView) findViewById(R.id.grouplistView);
		set = (ImageView) findViewById(R.id.set);
		add = (ImageView) findViewById(R.id.add);
		GroupAdapter ga = new GroupAdapter(this,getGroup());
		grouplistview.setAdapter(ga);
		grouplistview.setCacheColorHint(0);
		set.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadImage(GHomeActivity.this);
			}

			
		});
		add.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			uploadImage2(GHomeActivity.this);
    		}

    	});
	}
	
	private void uploadImage2(GHomeActivity gHomeActivity) {
		// TODO Auto-generated method stub
		 menuWindow2 = new SelectAddPopupWindow(GHomeActivity.this, itemsOnClick2);
		 //显示窗口
		 menuWindow2.showAtLocation(GHomeActivity.this.findViewById(R.id.add), Gravity.TOP|Gravity.RIGHT, 10, 230); //设置layout在PopupWindow中显示的位置
	}
	private OnClickListener  itemsOnClick2 = new OnClickListener(){
    	
    	public void onClick(View v) {
    		menuWindow2.dismiss();
    	}
    };
	private void uploadImage(GHomeActivity gHomeActivity) {
		// TODO Auto-generated method stub
		
	}
	private ArrayList<Group> getGroup(){
		ArrayList<Group> hhList = new ArrayList<Group>();
		Group h1 = new Group();
		h1.setTxPath(R.drawable.icon+"");
		h1.setName1("精英交流");
		h1.setLastContent("上季度销售额？");
		h1.setLastTime("下午 16:00");
		
		
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		hhList.add(h1);
		return hhList;
	} 
	
}
