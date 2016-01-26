package com.hypoxiagames.loosethreads;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

// This file is used to load files at runtime. This exists to load, and divide up regions on a spritesheet
//but hasn't been implemented yet.

public class Assets {
	private final static AssetManager manager = new AssetManager();
	

	public static void load() {
		Texture.setAssetManager(getManager());
		System.out.println("Loaded Human File");
		System.out.println("Loaded Main Menu Background");
		getManager().load("Screens/Background.png", Texture.class);
		getManager().load("Screens/MainMenuBackground.png", Texture.class);
		getManager().load("Sounds/Title/cringe.mp3",Music.class);
		getManager().load("Sounds/Home.mp3", Music.class);
		LoadFonts();
	}

	public static void dispose() {
		getManager().unload("Screens.MainMenuBackground");
		getManager().unload("Screens/Background.png");
		getManager().unload("Fonts/AVidaNova.fnt");
		getManager().unload("Fonts/DroidSans.fnt");
		getManager().unload("Sounds/Title/cring.wav");
		getManager().unload("Sounds/Home.mp3");
	}
	
	public static void loadTitleScreenAssets(){
		getManager().load("Screens/MainMenuBackground.png", Texture.class);
	}

	private static void LoadFonts() {
		System.out.println("Loading Fonts....");
		getManager().load("Fonts/AVidaNova.fnt", BitmapFont.class);
		getManager().load("Fonts/DroidSans.fnt", BitmapFont.class);
		System.out.println("Fonts Loaded!");

	}

	public static AssetManager getManager() {
		return manager;
	}

}
