package demo.bupt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import demo.Elearn.R;
import demo.bupt.adapter.CourseListViewAdapter;
import demo.bupt.course.ChapterActivity;

public class Act_course extends Activity {

	private ListView courselist;
	private CourseListViewAdapter CourseListViewAdapter;
	private List<Map<String, Object>> listItems;
	private Integer[] imgeIDs = { R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon };
	private String[] title = { "课程一", "课程二", "课程三", "课程四" };
	private String[] info = { "这是数学...", "这是语文...", "这是物理。。。", "这是化学。。。" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_select);

		courselist = (ListView) findViewById(R.id.course_list);
		listItems = getListItems();
		CourseListViewAdapter = new CourseListViewAdapter(this, listItems);
		courselist.setAdapter(CourseListViewAdapter);

		courselist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;

				intent = new Intent(Act_course.this, ChapterActivity.class);
				intent.putExtra("title", title[position]);
				startActivity(intent);

			}
		});

	}

	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < title.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imgeIDs[i]); // 图片资源
			map.put("title", title[i]); // 物品标题
			map.put("info", info[i]); // 物品名称

			listItems.add(map);
		}
		return listItems;
	}
}
