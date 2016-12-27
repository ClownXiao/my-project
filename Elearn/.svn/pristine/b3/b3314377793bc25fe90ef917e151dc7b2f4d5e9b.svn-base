package demo.bupt.myinfo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import demo.Elearn.R;

/**
 * @author fei 我的收藏夹
 * 
 */
public class Act_MyFavorite extends Activity {

	ListView favoriteList;
	ArrayList<String> favoriteData;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo__favorite);

		favoriteList = (ListView) findViewById(R.id.my_favorite_list);

		favoriteData = new ArrayList<String>();
		favoriteData.add("我的收藏一");
		favoriteData.add("我的收藏二");
		favoriteData.add("我的收藏三");
		favoriteData.add("我的收藏四");
		favoriteData.add("我的收藏五");
		favoriteData.add("我的收藏六");

		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View view = View.inflate(Act_MyFavorite.this,
						R.layout.myinfo_favorite_item, null);
				TextView tv = (TextView) view.findViewById(R.id.favorite_item);
				tv.setText(favoriteData.get(position));
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
				return favoriteData.size();
			}
		};
		favoriteList.setAdapter(adapter);
	}
}
