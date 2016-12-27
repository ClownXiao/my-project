package demo.bupt.groupchat;

import java.util.ArrayList;

import demo.Elearn.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Group> list = new ArrayList<Group>();
	
	public GroupAdapter(Context context,ArrayList<Group> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Group hh = list.get(position);
		H h = null;
		if(convertView==null){
			h = new H();
			convertView = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
			h.pic = (ImageView)convertView.findViewById(R.id.l1);
			h.name = (TextView)convertView.findViewById(R.id.name);
			h.time = (TextView)convertView.findViewById(R.id.time);
			h.lastmsg = (TextView)convertView.findViewById(R.id.lastmsg);
			
			convertView.setTag(h);
		}else{
			h = (H)convertView.getTag();
		}
		
		h.pic.setImageResource(Integer.parseInt(hh.getTxPath()));
		h.name.setText(hh.getName1());
		h.time.setText(hh.getLastTime());
		h.lastmsg.setText(hh.getLastContent());
		
		return convertView;
	}
	class H{
		ImageView pic;
		TextView name;
		TextView time;
		TextView lastmsg;
	}
}
