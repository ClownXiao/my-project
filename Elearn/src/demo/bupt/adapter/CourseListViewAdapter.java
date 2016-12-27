package demo.bupt.adapter;

import java.util.List;
import java.util.Map;

import demo.Elearn.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseListViewAdapter extends BaseAdapter {
	private Context context; // ����������
	private List<Map<String, Object>> listItems; // ��Ʒ��Ϣ����
	private LayoutInflater listContainer; // ��ͼ����

	public final class ListItemView { // �Զ���ؼ�����
		public ImageView image;
		public TextView title;
		public TextView info;
	}

	public CourseListViewAdapter(Context context,
			List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // ������ͼ����������������
		this.listItems = listItems;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("method", "getView");

		// �Զ�����ͼ
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.course_item, null);
			// ��ȡ�ؼ�����
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.imageItem);
			listItemView.title = (TextView) convertView
					.findViewById(R.id.titleItem);
			listItemView.info = (TextView) convertView
					.findViewById(R.id.infoItem);
			;
			// ���ÿؼ�����convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// Log.e("image", (String) listItems.get(position).get("title")); //����
		// Log.e("image", (String) listItems.get(position).get("info"));

		// �������ֺ�ͼƬ
		listItemView.image.setBackgroundResource((Integer) listItems.get(
				position).get("image"));
		listItemView.title.setText((String) listItems.get(position)
				.get("title"));
		listItemView.info.setText((String) listItems.get(position).get("info"));

		// ע���ѡ��״̬�¼�����

		return convertView;
	}

}
