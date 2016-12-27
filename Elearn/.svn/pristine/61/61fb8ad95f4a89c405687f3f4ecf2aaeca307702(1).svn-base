package demo.bupt;

import demo.Elearn.R;
import demo.bupt.test.testActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Act_test extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_select);
		ListView testlist = (ListView) findViewById(R.id.testlist);
		String[] arr = { "≤‚ ‘“ª", "≤‚ ‘∂˛", "≤‚ ‘»˝", "≤‚ ‘Àƒ" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, arr);
		testlist.setAdapter(arrayAdapter);
		testlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = null;
				switch ((int) id) {
				case 0:
					intent = new Intent(Act_test.this, testActivity.class);
					intent.putExtra("id", (int) id);
					startActivity(intent);
					break;
				}

			}
		});

	}
}
