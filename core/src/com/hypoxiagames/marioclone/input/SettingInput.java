package com.hypoxiagames.marioclone.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.hypoxiagames.marioclone.MainGame;

public class SettingInput implements InputProcessor {
	final MainGame game;
	boolean hovered;
	public boolean isLeftPressed;
	
	public SettingInput(final MainGame gam) {
		game = gam;
		
	}
	@Override
	public boolean keyDown(int keycode) {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) isLeftPressed = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(!Gdx.input.isKeyPressed(Keys.LEFT)) isLeftPressed = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println(screenX + " " + screenY);
		if(hovered)
			game.switchScreens("Main Menu");
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
		hovered = false;
		if(screenX >= 503 || screenX <= 903)
		{
			if(screenY >= 222 && screenY <= 249)
			{
				hovered = true;
			}
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}

}
