package demo.bupt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.Elearn.R;
import demo.bupt.adapter.FriendMenuListAdapter;
import demo.bupt.communication.FHomeActivity;
import demo.bupt.course.ChapterActivity;
import demo.bupt.groupchat.GHomeActivity;
import demo.bupt.test.testActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Act_friends extends Activity {
	private ListView friendList;
	private List<Map<String,Object>> listItems;
	 private Integer[] imgeIDs = {R.drawable.friendshare,R.drawable.friendgroup,R.drawable.friendchat};
     private String[] title={"同学圈","导师讨论组","同学交流"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_menu);
		
		friendList = (ListView) findViewById(R.id.friend_menu);
		listItems = getListItems();
		FriendMenuListAdapter adapter = new FriendMenuListAdapter(this,listItems);
		friendList.setAdapter(adapter);
		friendList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> patent, View view, int position,
					long id) {
				Intent intent = new Intent();
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					intent = new Intent(Act_friends.this,FHomeActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(Act_friends.this,GHomeActivity.class);
					startActivity(intent);
					break;
				case 2:
					
				}
			}
			
		});
	
	}
		
	private List<Map<String, Object>> getListItems() {   
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();   
        for(int i = 0; i < title.length; i++) {   
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("image", imgeIDs[i]);               //图片资源   
            map.put("title", title[i]);              //物品标题   
           
            listItems.add(map);   
        }      
        return listItems;   
    }  
}
