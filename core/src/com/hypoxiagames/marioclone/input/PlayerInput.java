package com.hypoxiagames.marioclone.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.hypoxiagames.marioclone.MainGame;

public class PlayerInput implements InputProcessor {
	final MainGame game;
	public static boolean leftKeyDown, rightKeyDown, upKeyDown, downKeyDown;

	public PlayerInput(MainGame gam) {
		game = gam;
		leftKeyDown = false;
		rightKeyDown = false;
		upKeyDown = false;
		downKeyDown = false;

	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case (Keys.UP):
			upKeyDown = true;
			break;
		case (Keys.DOWN):
			downKeyDown = true;
			break;
		case (Keys.LEFT):
			leftKeyDown = true;
			break;
		case (Keys.RIGHT):
			rightKeyDown = true;
			break;

		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case (Keys.UP):
			upKeyDown = false;
			break;
		case (Keys.DOWN):
			downKeyDown = false;
			break;
		case (Keys.LEFT):
			leftKeyDown = false;
			break;
		case (Keys.RIGHT):
			rightKeyDown = false;
			break;

		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
