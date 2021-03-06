package demo.bupt;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import demo.Elearn.R;

public class Elearn extends TabActivity  {
	 private TabHost _tabHost;
	
	
	/** Called when the activity is first created. */
	 /*@Override
	 public boolean dispatchKeyEvent(KeyEvent event) {  
		    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK  
		            && event.getAction() == KeyEvent.ACTION_DOWN  
		            && event.getRepeatCount() == 0) {             
		        //具体的操作代码  
		    	return false;
		    }  
		    return super.dispatchKeyEvent(event);  
		} */
	 
	 @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		 
		        super.onStart();  
		        checkNetWork();  
		  
		   
	 }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.main);
        
        _tabHost = getTabHost();
        
     
      
        
        AddTabPage1();
        
        AddTabPage2();
        
        AddTabPage3();
        
        AddTabPage4();
      
    }
    
    

	private void AddTabPage1() {
		// TODO Auto-generated method stub
		
		Intent internt1 = new Intent();
		internt1.setClass(this,Act_test.class);
		
		TabSpec tabSpec = _tabHost.newTabSpec("act1");
		//指定选项卡的显示名称
		tabSpec.setIndicator("测试");
		//指定跳转方向
		tabSpec.setContent(internt1);          
		_tabHost.addTab(tabSpec);
	
	}
	
	private void AddTabPage2() {
		// TODO Auto-generated method stub
		
		Intent internt1 = new Intent();
		internt1.setClass(this,Act_course.class);
		
		TabSpec tabSpec = _tabHost.newTabSpec("act2");
		tabSpec.setIndicator("课程");
		tabSpec.setContent(internt1);          
		_tabHost.addTab(tabSpec);
	}
	private void AddTabPage3() {
		// TODO Auto-generated method stub
		
		Intent internt1 = new Intent();
		internt1.setClass(this,Act_friends.class);
		
		TabSpec tabSpec = _tabHost.newTabSpec("act3");
		tabSpec.setIndicator("交流圈");
		tabSpec.setContent(internt1);          
		_tabHost.addTab(tabSpec);
	}
	private void AddTabPage4() {
		// TODO Auto-generated method stub
		
		Intent internt1 = new Intent();
		internt1.setClass(this,Act_myInfo.class);
		
		TabSpec tabSpec = _tabHost.newTabSpec("act4");
		tabSpec.setIndicator("我");
		tabSpec.setContent(internt1);          
		_tabHost.addTab(tabSpec);
	}
	 public void checkNetWork() {  
	        // 获取 连接的管理对象  
	        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  
	  
	        // 获取当前正在使用的网络  
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
	        // 判断网络是否可用  
	        if (networkInfo != null && networkInfo.isConnected()) {  
	            // 网络连接  
	            if ( ConnectivityManager.TYPE_MOBILE== networkInfo.getType()) { 
	            	Toast toast;
	                toast = Toast.makeText(Elearn.this, "正在使用手机流量",  
	                        Toast.LENGTH_SHORT);  
	                toast.setGravity(Gravity.CENTER, 0, 0);
	                toast.show();
	                
	            }  else if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()  ) {  
		            // wifi  
		        	Toast toast;
		            toast = Toast.makeText(Elearn.this, "正在使用wifi", Toast.LENGTH_SHORT) ;
		            toast.setGravity(Gravity.CENTER, 0, 0);
		            toast .show();  
		  
		        } 
	        } else {  
	            // 网络没有连接的情况  
	        	AlertDialog.Builder builder = new AlertDialog.Builder(Elearn.this);
	            builder  
	                    .setTitle("请设置网络连接")  
	                    .setMessage("网络没有打开。请进行设置。")  
	                    .setIcon(R.drawable.wireless64)  
	                    .setPositiveButton("取消",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                @Override  
	                                public void onClick(DialogInterface arg0,  
	                                        int arg1) {  
	  
	                                    Toast.makeText(Elearn.this, "取消",  
	                                            Toast.LENGTH_SHORT).show();  
	                                }  
	                            })  
	                    .setNegativeButton("去设置",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                @Override  
	                                public void onClick(DialogInterface dialog,  
	                                        int which) {  
	                                    Intent intent = new Intent(  
	                                            android.provider.Settings.ACTION_WIFI_SETTINGS);  
	                                }  
	                            }).show();  
	        }  
	    }  
	}
