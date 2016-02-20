package com.hypoxiagames.loosethreads;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.hypoxiagames.loosethreads.screens.*;

public class MainGame extends Game {
	//Declarations for all screen states.
	// Each screen handles a certain task.
	
	TitleScreen mainMenuScreen;
	SettingsScreen settingsScreen;
	SaveSelectionScreen saveScreen;
	GameScreen gameScreen;

	
	public int screenX, screenY;

	public void exit(){
		System.out.println("Exiting game now!");
		Assets.getManager().dispose();
		Gdx.app.exit();
	}
	
	public void switchScreens(String ScreenName){
		switch(ScreenName){
		case "Main Menu":
			mainMenuScreen = new TitleScreen(this);
			this.setScreen(mainMenuScreen);
			break;
		
		case "Settings":
			settingsScreen = new SettingsScreen(this);
			this.setScreen(settingsScreen);
			break;
		case "Save Selection":
			saveScreen = new SaveSelectionScreen(this);
			this.setScreen(saveScreen);
			break;
		case "Game Screen":
			gameScreen = new GameScreen(this);
			this.setScreen(gameScreen);
			break;
			
		}
	}
	
	public void setMainScreen(boolean isNewGame){
		this.getScreen().dispose();
		Assets.loadTitleScreenAssets();
		this.setScreen(new TitleScreen(this));
	}
	public void setGameScreen(boolean isNewGame){
		this.getScreen().dispose();
		this.setScreen(new GameScreen(this));
	}
	
	
	
	public void create() {
		Assets.load();
		screenX = Gdx.graphics.getWidth();
		screenY = Gdx.graphics.getHeight();
		switchScreens("Main Menu");
		
		
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		
	}
	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	Music music;

}
