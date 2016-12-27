package demo.bupt.myinfo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import demo.Elearn.R;

/**
 * @author fei 我的学习记录
 */
public class Act_StudyRecord extends Activity {

	ListView recordList;
	List<String> recordDatas;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo_studyrecord);

		recordList = (ListView) findViewById(R.id.study_record_list);

		recordDatas = new ArrayList<String>();
		recordDatas.add("第一课");
		recordDatas.add("第二课");
		recordDatas.add("第三课");
		recordDatas.add("第五课");
		recordDatas.add("第七课");
		recordDatas.add("第八课");
		
		
		BaseAdapter adapter=new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View view=View.inflate(Act_StudyRecord.this, R.layout.study_record_item, null);
				TextView tv=(TextView) view.findViewById(R.id.record);
				tv.setText(recordDatas.get(position));
				return view;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return recordDatas.size();
			}
		};
		recordList.setAdapter(adapter);
	}

}
