package demo.bupt.course;

import java.io.File;

import demo.Elearn.R;
import demo.bupt.course.VideoPlay.ask;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class videofull extends Activity {
	VideoView videoView;
	MediaController mController;
	Button button1,button2;
	String video_path = "/storage/extSdCard/DCIM/Temp.mp4";
	File video = new File(video_path);
	boolean fullscreen = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		setContentView(R.layout.video_full);
        videoView = (VideoView) findViewById(R.id.audtoView);
		mController = new MediaController(this);
		button1 = (Button)findViewById(R.id.audio_2_video);
		//button1.setOnClickListener(new turn());

		if (video.exists()) {
			videoView.setVideoPath(video.getAbsolutePath());
			videoView.setMediaController(mController);
			mController.setMediaPlayer(videoView);
			videoView.requestFocus();
			videoView.start();
		}
	}
	
	class turn  implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!fullscreen){//设置RelativeLayout的全屏模式  
			       RelativeLayout.LayoutParams layoutParams=  
			              new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);  
			           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);  
			           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);  
			           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);  
			           layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
			           videoView.setLayoutParams(layoutParams);  
			             
			           fullscreen = true;//改变全屏/窗口的标记  
			       }else{//设置RelativeLayout的窗口模式  
			          RelativeLayout.LayoutParams lp=new  RelativeLayout.LayoutParams(320,240);  
			          lp.addRule(RelativeLayout.CENTER_IN_PARENT);  
			          videoView.setLayoutParams(lp);  
			            fullscreen = false;//改变全屏/窗口的标记  
			       }    
		}
	}
		


}
