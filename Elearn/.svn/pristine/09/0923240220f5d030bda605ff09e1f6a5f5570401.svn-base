package demo.bupt.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import demo.Elearn.R;
import demo.bupt.adapter.CourseListViewAdapter.ListItemView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendMenuListAdapter extends BaseAdapter {
	private LayoutInflater inflater; // 视图容器
	private Context context; // 上下文
	private List<Map<String, Object>> listItems; // 信息集合

	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
	}

	// public static final List<String> more_list = new
	// ArrayList<String>();//为条目提供名称
	public FriendMenuListAdapter(Context context,
			List<Map<String, Object>> listItems) {
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		/*
		 * more_list.add(context.getResources().getString(R.string.friend_item1))
		 * ;
		 * more_list.add(context.getResources().getString(R.string.friend_item2
		 * ));
		 * more_list.add(context.getResources().getString(R.string.friend_item3
		 * ));
		 */
		this.listItems = listItems;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/*
		 * if(converView == null){ converView =
		 * inflater.inflate(R.layout.friend_menu_item, null);
		 * 
		 * } ImageView icon = (ImageView)
		 * converView.findViewById(R.id.friend_menu_item_icon); if(0 ==
		 * position) { icon.setBackgroundResource(R.drawable.friendshare); }else
		 * if(1 == position){
		 * icon.setBackgroundResource(R.drawable.friendgroup); }else if(2 ==
		 * position){ icon.setBackgroundResource(R.drawable.friendchat); }
		 * TextView text = (TextView)
		 * converView.findViewById(R.id.friend_menu_item_text);
		 * text.setText(more_list.get(position)); return converView;
		 */
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = inflater.inflate(R.layout.friend_menu_item, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.friend_menu_item_icon);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.friend_menu_item_text);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置图片和title
		listItemView.image.setBackgroundResource((Integer) listItems.get(
				position).get("image"));
		listItemView.title.setText((String) listItems.get(position)
				.get("title"));
		return convertView;

	}

}
