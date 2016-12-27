package demo.bupt.course;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import demo.Elearn.R;
import demo.bupt.course.video_test.askclick;
import demo.bupt.test.testActivity;


public class VideoPlay extends Activity {
	VideoView videoView;
	MediaController mController;
	Button button1,button2;
	String video_path = "/storage/extSdCard/DCIM/learn.mp4";
	File video = new File(video_path);
	TextView notice,tishi;
	LinearLayout l;
	static boolean menu = false;
	
	@Override
	protected void onRestart(){
		super.onRestart();
		SharedPreferences playtime = getSharedPreferences("user_info", 0);
		int currenttime = playtime.getInt("currenttime",0);
		if (currenttime != 0) {  
	  
	        videoView.seekTo(currenttime);  
	        videoView.start();
	    }  
		else videoView.start();
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.postDelayed(this, 1000);
				if (videoView.getCurrentPosition() >=123000&&videoView.getCurrentPosition() <=124000) {// 6000为测试数据，对应6S
					//handler.removeCallbacks(this);
					notice.setVisibility(TextView.VISIBLE);
					Animation t1 = new TranslateAnimation(500.0f,0.0f,0.0f,0.0f);  
				    t1.setDuration(2000);
				    t1.setAnimationListener(new Animation.AnimationListener() {

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							ScaleAnimation s1 = new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f,
						             Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f); 
						    s1.setDuration(2000);
							notice.startAnimation(s1);
							s1.setAnimationListener(new Animation.AnimationListener() {
								@Override
								public void onAnimationEnd(Animation animation) {
									Animation t2 = new TranslateAnimation(0.0f,-400.0f,0.0f,0.0f);  
								    t2.setDuration(2000);
								    notice.startAnimation(t2);
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub
									
								}
							});
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							
						}
				    	
				    });
				    
				    
				    AnimationSet a = new AnimationSet(true);
				    a.addAnimation(t1);
					notice.startAnimation(a);
					notice.setVisibility(TextView.INVISIBLE);
				}
				
				if (videoView.getCurrentPosition() >= 161000&&videoView.getCurrentPosition() <= 162000) {
					
					tishi.setText("小贴士:\nwindows resources\n可在loadRunner下\n进行监控");
					tishi.setVisibility(TextView.VISIBLE);
				}
				if (videoView.getCurrentPosition() >= 165000&&videoView.getCurrentPosition() <= 166000) {
					handler.removeCallbacks(this);
					tishi.setVisibility(TextView.INVISIBLE);
					
				}
				
				;

			}
		};
		handler.postDelayed(runnable, 1000);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		setContentView(R.layout.video);
        videoView = (VideoView) findViewById(R.id.course_video);
		mController = new MediaController(this);
		TextView tex = (TextView) findViewById(R.id.textView3);
		tex.setText("视频名称:"+video_path);
		notice = (TextView) findViewById(R.id.textView4);
		tishi = (TextView) findViewById(R.id.tishi);
		
		button2 = (Button)findViewById(R.id.button2);
		
		button2.setOnClickListener(new ask());
		
		l = (LinearLayout)findViewById(R.id.videolayout);
		
		videoView.setOnTouchListener(new OnTouchListener()  
		{  
            
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(!menu){
				l.setVisibility(mController.VISIBLE);
				menu = true;}
				else{
					l.setVisibility(mController.INVISIBLE);
					menu = false;
				}
				return false;
			}  
		});  
		
		
		if (video.exists()) {
			videoView.setVideoPath(video.getAbsolutePath());
			videoView.setMediaController(mController);
			mController.setMediaPlayer(videoView);
			videoView.requestFocus();
			videoView.start();

			final Handler handler = new Handler();
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.postDelayed(this, 1000);
					if (videoView.getCurrentPosition() >= 20000&&videoView.getCurrentPosition() <= 21000) {// 3000为测试数据，对应3S
						handler.removeCallbacks(this);
						videoView.pause();
						//AlertDialog ad = question.create();
						//ad.show();
						SharedPreferences playtime = getSharedPreferences("user_info", 0);
						playtime.edit().putInt("currenttime", videoView.getCurrentPosition()).commit();
						Intent intent = null;

						intent = new Intent(VideoPlay.this, video_test.class);
						startActivity(intent);
                        
					}
					;

				}
			};
			handler.postDelayed(runnable, 1000);
			
			
			
			

		}
		
		
		 
		/*final AlertDialog.Builder ask = new AlertDialog.Builder(this);
		 ask.setTitle("请输入") 
		 .setIcon(android.R.drawable.ic_dialog_info)  
		 .setView(new EditText(this))  
		 .setPositiveButton("确定", null)  
		 .setNegativeButton("取消", null);*/
		
		/*final AlertDialog.Builder right = new AlertDialog.Builder(this);
		 right.setMessage("不错哦！你的答案正确!");
		 right.setTitle("提示");
		 right.setPositiveButton("确认", new OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    videoView.start();
			   }
			  });
		 
		final AlertDialog.Builder wrong = new AlertDialog.Builder(this);
		wrong.setMessage("选错拉！？哪些地方不懂呢？\n需要提问么?");
		 wrong.setTitle("提示");
		wrong.setPositiveButton("需要", new OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    ask.create().show();
			   }
			  });
		 wrong.setNegativeButton("不需要", new OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			   }
			  });*/
		 
		 
		 
			
		/*final AlertDialog.Builder question = new AlertDialog.Builder(this);
		final String items[] = new  String[] { "1", "2", "3", "4" };
		question.setTitle("1+1=?")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(items, 0,
						new DialogInterface.OnClickListener() {
                           @Override
							public void onClick(DialogInterface dialog,
									int which) {
								if(items[which] == "2"){
									right.create().show();
									dialog.dismiss();
									
								}
								else{
									wrong.create().show();
									dialog.dismiss();
								}
							}
						})
						.setNegativeButton("取消", null);*/

		
	}
	
	class ask implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder ask = new AlertDialog.Builder(VideoPlay.this);
			 ask.setTitle("请输入") 
			 .setIcon(android.R.drawable.ic_dialog_info)  
			 .setView(new EditText(VideoPlay.this))  
			 .setPositiveButton("确定", null)  
			 .setNegativeButton("取消", null);
			ask.show();
		}
	}
	

	

}