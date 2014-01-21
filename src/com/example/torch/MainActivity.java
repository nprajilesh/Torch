package com.example.torch;

import java.net.MalformedURLException;
import java.net.URL;

import com.nativecss.NativeCSS;
import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	Button b;
	float y1,y2;
	private Camera camera;
	Parameters params;
	
	private boolean hasFlash;
	private boolean isFlashOn = false;
	private Parameters p;
	private MediaPlayer mp;
	 static int i=0;
	 ImageView img;

	 
	 void checkflash()
	 {
		 hasFlash = getApplicationContext().getPackageManager()
					.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		

			if (!hasFlash)
			{
				// device doesn't support flash
				// Show alert message and close the application
			
//				Toast.makeText(getApplicationContext(), "No Flash Detected !!", Toast.LENGTH_LONG).show();
				AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
				.create();
			alert.setTitle("Error");
			alert.setMessage("Sorry, your device doesn't support flash light!");
			alert.setButton("OK", new DialogInterface.OnClickListener() 
			{
					public void onClick(DialogInterface dialog, int which)
					{
							// closing the application
							finish();
					}
			});
				alert.show();
				
		
			}
		
	 }
	
	//case 0
	 //button
	 
	 public void bclick(View v)
	 {
			b=(Button) v;
		
		
			 if(b.getText().equals("on"))
			 {
				 flashon();
				 b.setText("off");
				
			 }
			 else
			 {
				 b.setText("on");
				 flashoff();
			 }
		 	
	}		
	 
	 
	 
 //case 1
	 //Swipe
	 
		 public boolean onTouchEvent(MotionEvent m)
		 {
			 if(i==1)
			 {
				 checkflash();
				// startlayout();
				 switch(m.getAction())
				{
				 	case MotionEvent.ACTION_DOWN: 
				 	
				 		y1 = m.getY();
				 			// Toast.makeText(getApplicationContext(), "y1= "+y1, 250).show();
				 			break;
				 	
          
				 	case MotionEvent.ACTION_UP: 
				 	
				 		y2 = m.getY();
				 		// Toast.makeText(getApplicationContext(), "y2= "+y2, 250).show();
              
        
				 		//swap Up
				 		if(y1>y2)
				 		{
				 			setContentView(R.layout.turnon);
	//			 			Drawable drawable=this.getResources().getDrawable(R.drawable.btn_switch_on);
				 			flashon();
				 		}
				 		else
				 		{
				 			setContentView(R.layout.activity_main);	
				 		//	Drawable drawable=this.getResources().getDrawable(R.drawable.btn_switch_off);
				 			//this.img.setBackground(drawable);
				 			flashoff();
				 		}
				 		break;
				 	
          
				}
			 
			 }	 
		return true;
		
		}
	
	 
		 void flashon()
		 {
			 if(!isFlashOn )
       	  	{
       		  
       		  	isFlashOn = true;
       		 		makesound();
       		 
         		    final Toast toast =Toast.makeText(getApplicationContext(), "Flash On ", Toast.LENGTH_SHORT);
        		    toast.show();
        		    Handler handler = new Handler();
        	        handler.postDelayed(new Runnable()
        	        {
        	           @Override
        	           public void run() 
        	           {
        	               toast.cancel(); 
        	           }
        	        }, 1000);
        	        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
        	        camera.setParameters(p);
        	        camera.startPreview();
       	  }
       	  else
       	  {
       		  final Toast toast =Toast.makeText(getApplicationContext(), "Swipe Down to Turn Off Flash", Toast.LENGTH_SHORT);
       		  toast.show();
      		    Handler handler = new Handler();
      	        handler.postDelayed(new Runnable() {
      	           @Override
      	           public void run() {
      	               toast.cancel(); 
      	           }
      	        }, 1000);
       	  }

		 }
	
	void flashoff()
	{
		if(isFlashOn)
	  {
		  isFlashOn = false;	
		  p = camera.getParameters();
			

		  
		  makesound();
		   final Toast toast =Toast.makeText(getApplicationContext(), "Flash off ", Toast.LENGTH_SHORT);
		    toast.show();
		    Handler handler = new Handler();
	        handler.postDelayed(new Runnable()
	        {
	           @Override
	           public void run() 
	           {
	               toast.cancel(); 
	           }
	        }, 1000);
		 
		  p.setFlashMode(Parameters.FLASH_MODE_OFF);
		  camera.setParameters(p);
		  camera.stopPreview();
		 
	  }
	  else
	  {
		   final Toast toast =Toast.makeText(getApplicationContext(), "Swipe Up To Turn On Flash ", Toast.LENGTH_SHORT);
		    toast.show();
		    Handler handler = new Handler();
	        handler.postDelayed(new Runnable()
	        {
	           @Override
	           public void run() 
	           {
	               toast.cancel(); 
	           }
	        }, 1000);
		 
	 
	  }
	  

	}
	
	
	public void makesound()
	{
		try
		  {
			  mp = MediaPlayer.create(getApplicationContext(), R.raw.off); mp.start();
		  }
		  catch(Exception e)
		  {
			  
		  }
	}
	
	public void onBackPressed()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
	alert.setTitle("Exit");
	alert.setMessage("Press Ok to Exit Torch");
	alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() 
	{
			public void onClick(DialogInterface dialog, int which)
			{
					// closing the application
					dialog.cancel();
			}
	});
	alert.setNegativeButton("OK", new DialogInterface.OnClickListener() 
	{
			public void onClick(DialogInterface dialog, int which)
			{
					p = camera.getParameters();
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					camera.stopPreview();
					camera.release();
					dialog.cancel();
					finish();

			}
	});
		alert.show();
	}

		

	public void startlayout()
	{
		if(i==1)
			setContentView(R.layout.activity_main);
		else if(i==0)
			setContentView(R.layout.button);
	}
	
	
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	//	img=(ImageView)this.findViewById(R.id.img);
		
			try
			{
			
			camera = Camera.open();
			p = camera.getParameters();
			}
			catch(Exception e)
			{
			}
			
		
		startlayout();
		Log.d("i=main",""+MainActivity.i);
		 
		URL css;
		try {
			css = new URL("http://10.0.2.2:8000/style.css");
			NativeCSS.styleWithCSS("style.css",css,Never);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

         
	//	setContentView(R.layout.button);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean isHasFlash() {
		return hasFlash;
	}

	public void setHasFlash(boolean hasFlash) {
		this.hasFlash = hasFlash;
	}
	
	

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.it :
			
			Intent i=new Intent(this,New.class);
			startActivity(i);
			camera.release();
			finish();
		
			return true;
			default:
				return super.onOptionsItemSelected(item);
		
		}
	}
	

}
