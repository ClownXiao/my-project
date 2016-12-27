package demo.bupt.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import demo.Elearn.R;
import demo.bupt.Act_course;

public class ChapterActivity extends Activity {
	ListView chapter;
	String[] arr = { "第一章", "第二章", "第三章", "第四章", "第五章" };
	TextView title;
	String intentValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chapter);

		title = (TextView) findViewById(R.id.course_name);
		chapter = (ListView) findViewById(R.id.chapter_list);
		intentValue = getIntent().getStringExtra("title");

		title.setText(intentValue);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		chapter.setAdapter(arrayAdapter);
		chapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				intent = new Intent(ChapterActivity.this, CourseDetail.class);
				intent.putExtra("course", intentValue);
				intent.putExtra("chapter", arr[position]);
				startActivity(intent);

			}
		});
	}

}
