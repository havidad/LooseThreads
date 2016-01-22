package com.hypoxiagames.loosethreads.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.hypoxiagames.loosethreads.MainGame;


public class SaveSelectionInput implements com.badlogic.gdx.InputProcessor{
	final MainGame game;
	public SaveSelectionInput(final MainGame gam){
		game = gam;
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
		
	}

	@Override
	public boolean keyUp(int keycode) {
		if(Gdx.input.isKeyPressed(Keys.ENTER))
			game.switchScreens("Main Menu");
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}

}
