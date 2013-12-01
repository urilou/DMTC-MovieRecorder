package com.example.startui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateKey extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_createkey);
		
		ImageButton shr = (ImageButton) findViewById(R.id.share);
		shr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder
						.from(CreateKey.this);
				// 共有する文字列
				builder.setText("キーを発行しました：122FEW");
				builder.setType("text/plain");
				builder.startChooser();
			}
		});

		ImageButton btn = (ImageButton) findViewById(R.id.camera);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreateKey.this,
						CameraPreview.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
