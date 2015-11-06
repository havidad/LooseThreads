package com.hypoxiagames.marioclone;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	private final static AssetManager manager = new AssetManager();
	public static final String human = "Sprites/race2.png";

	public static void load() {
		Texture.setAssetManager(getManager());

		getManager().load(human, Texture.class);
		System.out.println("Loaded Human File");
		getManager().load("Screens/mainMenuBackground.png", Texture.class);
		System.out.println("Loaded Main Menu Background");
		LoadFonts();
	}

	public static void dispose() {
		getManager().unload(human);
		getManager().unload("Screens/mainMenuBackground.png");
		getManager().unload("Fonts/AVidaNova.fnt");
		getManager().unload("Fonts/DroidSans.fnt");
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
