package com.hypoxiagames.marioclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.hypoxiagames.marioclone.screens.*;

public class MainGame extends Game {
	TitleScreenNew mainMenuScreen;
	TitleScreen mainMenuScreenOld;
	SettingsScreen settingsScreen;
	SaveSelectionScreen saveScreen;
	GameScreen gameScreen;
	
	public int screenX, screenY;

	public void exit(){
		System.out.println("Exiting game now!");
		Gdx.app.exit();
	}
	
	public void switchScreens(String ScreenName){
		switch(ScreenName){
		case "Main Menu":
			mainMenuScreen = new TitleScreenNew(this);
			setScreen(mainMenuScreen);
			break;
		
		case "Settings":
			settingsScreen = new SettingsScreen(this);
			setScreen(settingsScreen);
			break;
		case "Save Selection":
			saveScreen = new SaveSelectionScreen(this);
			setScreen(saveScreen);
			break;
		case "Game Screen":
			gameScreen = new GameScreen(this);
			setScreen(gameScreen);
			break;
			
		}
	}
	
	
	
	public void create() {
		screenX = Gdx.graphics.getWidth();
		screenY = Gdx.graphics.getHeight();
		switchScreens("Main Menu");
		
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		
	}

}
