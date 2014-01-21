package com.example.torch;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class New extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		Log.d("i=",""+MainActivity.i);
		
		
	}
	
	

	public void onRadioButtonClicked(View view)
	{
		RadioGroup rg = (RadioGroup)findViewById(R.id.rgrp);
		RadioButton rb =((RadioButton)findViewById(rg.getCheckedRadioButtonId() ));
		if(rb.getText().equals("Button"))
		{
			MainActivity.i=0;
		}
		else if(rb.getText().equals("Swipe"))
		{
			MainActivity.i=1;
		}
		
		
			final Toast toast =Toast.makeText(getApplicationContext(), rb.getText().toString(), Toast.LENGTH_SHORT);
			toast.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run() 
				{
					toast.cancel(); 
				}
			}, 500);
		
        
		Log.d("i=",""+MainActivity.i);
		//	MainActivity.startlayout();
		Intent it=new Intent(this,MainActivity.class);
		startActivity(it);
			finish();
	}
}
