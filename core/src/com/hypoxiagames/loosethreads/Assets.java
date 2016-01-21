package com.hypoxiagames.loosethreads;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

// This file is used to load files at runtime. This exists to load, and divide up regions on a spritesheet
//but hasn't been implemented yet.

public class Assets {
	private final static AssetManager manager = new AssetManager();
	private static final String human = "Sprites/race2.png";
	private static final String finn = "Sprites/Finn.png";

	public static void load() {
		Texture.setAssetManager(getManager());

		getManager().load(human, Texture.class);
		getManager().load(finn, Texture.class);
		System.out.println("Loaded Human File");
		System.out.println("Loaded Main Menu Background");
		getManager().load("Screens/Background.png", Texture.class);
		getManager().load("Screens/MainMenuBackground.png", Texture.class);
		LoadFonts();
	}

	public static void dispose() {
		getManager().unload(finn);
		getManager().unload(human);
		getManager().unload("Screens.MainMenuBackground");
		getManager().unload("Screens/Background.png");
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
