package com.hypoxiagames.loosethreads.input;

import com.badlogic.gdx.Input.Keys;
import com.hypoxiagames.loosethreads.MainGame;
import com.hypoxiagames.loosethreads.screens.*;

public class TitleInput implements com.badlogic.gdx.InputProcessor {
	final MainGame game;
	final TitleScreen screen;
	public static boolean upButtonPressed, downButtonPressed, leftButtonPressed, rightButtonPressed;

	public TitleInput(final MainGame gam, TitleScreen screen) {
		game = gam;
		this.screen = screen;
		upButtonPressed = false;
		downButtonPressed = false;
		leftButtonPressed = false;
		rightButtonPressed = false;

	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.ENTER:
			switch (TitleScreen.getItemSelected()) {
			case 0:
				game.switchScreens("Game Screen");
				break;
			case 1:
				game.switchScreens("Settings");
				break;
			case 2:
				System.out.println("Extras Pressed");
				break;
			case 3:
				game.exit();
				break;
			}
			break;
		case Keys.UP:
			upButtonPressed = true;
			break;
		case Keys.DOWN:
			downButtonPressed = true;
			break;
		case Keys.RIGHT:
			rightButtonPressed = true;
			break;
		case Keys.LEFT:
			leftButtonPressed = true;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			upButtonPressed = false;
			break;
		case Keys.DOWN:
			downButtonPressed = false;
			break;
		case Keys.RIGHT:
			rightButtonPressed = false;
			break;
		case Keys.LEFT:
			leftButtonPressed = false;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		System.out.println("Touched at X: " + screenX + ", Y: " + screenY + ". Button " + button + " was pressed.");
		return true;
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