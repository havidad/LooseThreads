package com.hypoxiagames.marioclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hypoxiagames.marioclone.MainGame;

public class DesktopLauncher {
	static LwjglApplicationConfiguration cfg;
	public  void changeResolution(int width, int height){
		cfg.width = width;
		cfg.height = height;
	}
	public static void main (String[] arg) {
		cfg = new LwjglApplicationConfiguration();
		cfg.title = "Project: Mafia";
		cfg.useGL30 = true;
		cfg.width = 1024;
		cfg.height = 768;
		cfg.resizable = true;
		new LwjglApplication(new MainGame(), cfg);
	}
}