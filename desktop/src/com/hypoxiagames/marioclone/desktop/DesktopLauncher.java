package com.hypoxiagames.marioclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hypoxiagames.loosethreads.MainGame;

public class DesktopLauncher {
	static LwjglApplicationConfiguration cfg;
	public  void changeResolution(int width, int height){
		cfg.width = width;
		cfg.height = height;
	}
	public static void main (String[] arg) {
		cfg = new LwjglApplicationConfiguration();
		cfg.title = "Loose Threads";
		cfg.useGL30 = false;
		cfg.width = 800;
		cfg.height = 640;
		cfg.resizable = true;
		new LwjglApplication(new MainGame(), cfg);
	}
}