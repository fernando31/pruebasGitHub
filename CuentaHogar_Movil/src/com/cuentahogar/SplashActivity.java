package com.cuentahogar;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends MainActivity {
	
	private TextView topTitle1;
	private TextView topTitle2;
	private TextView subTitle;
	private ImageView image1;
	private ImageView image2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Activity", "Arrancando SplashActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slpash);
		
		SharedPreferences settings = getSharedPreferences(CUENTAHOGAR_PREFERENCES, MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("EEE dd/MM/yyyy kk:mm:ss z");
		Date fecha = new Date();
		
		if(settings.contains("lastLaunch") == true){
			String lastLaunch = settings.getString("lastLaunch","Default");
			Log.i("lastLaunch", lastLaunch);
			
			prefEditor.putString("lastLaunch", formatoFecha.format(fecha));
			prefEditor.commit();
		}else {
			Log.i("lastLaunch", "First Launch");
			prefEditor.putString("lastLaunch", formatoFecha.format(fecha));
			prefEditor.commit();
		}
		
		this.startAnimation();
	}
	
	private void startAnimation(){
		
		topTitle1 = (TextView) findViewById(R.id.SplashTextViewTitle1); 
		topTitle2 = (TextView) findViewById(R.id.SplashTextViewTitle2);
		Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		topTitle1.startAnimation(fade1);
		topTitle2.startAnimation(fade1);
		
		image1 = (ImageView) findViewById(R.id.SplashImageView1);
		Animation transLeft = AnimationUtils.loadAnimation(this, R.anim.trans_left);
		image1.startAnimation(transLeft);
		
		subTitle = (TextView) findViewById(R.id.SplashTextViewSubtitle);
		image2 = (ImageView) findViewById(R.id.SplashImageView2);
		Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
		subTitle.startAnimation(fade2);
		image2.startAnimation(fade2);
			
		fade2.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this, MenuActivity.class));
				SplashActivity.this.finish();
				Log.i("Activity", "Cerrando SplashActivity");
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

	}
	
	protected void onPause() {
		super.onPause();
		Log.i("Activity", "SplashActivity en Pausa");
		topTitle1.clearAnimation();
		topTitle2.clearAnimation();
		image2.clearAnimation();
		image1.clearAnimation();		
	}
	
	protected void onResume() {
		super.onResume();
		Log.i("Activity", "SplashActivity Reanudando");
		this.startAnimation();
	}

}
