package ru.geekbrains.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import ru.geekbrains.android.StarWarsGame;

public class AndroidLauncher extends AndroidApplication {

//	private AudioService audioService;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StarWarsGame(), config);
//		audioService = new AudioService();
//		audioService.onCreate();
//		audioService.onStart(new Intent(this, AudioService.class), 5);
//		audioService.player.start();
		//startService(new Intent(this, AudioService.class));
	}

//	public void onBackPressed() {
//		stopService(new Intent(this, AudioService.class));
//	}
}
