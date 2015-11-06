package com.hypoxiagames.marioclone.input;

import com.hypoxiagames.marioclone.MainGame;
import com.hypoxiagames.marioclone.screens.*;
import com.badlogic.gdx.Input.Keys;

public class MenuInput implements com.badlogic.gdx.InputProcessor{
	final MainGame game;

	public MenuInput(final MainGame gam) {
		game = gam;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.ENTER:
			switch (MainMenuScreen.getItemSelected()) {
			case 0:
				// Set up transition to the game screen.
				System.out.println("Play Now Pressed");
				break;
			case 1:
				System.out.println("Settings Pressed");
				break;
			case 2:
				System.out.println("Extras Pressed");
				break;
			case 3:
				System.out.println("Exit Pressed");
				System.exit(0);
				break;
			}
			break;
		case Keys.UP:
			break;
		case Keys.DOWN:
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
		System.out.println("Touched at X: " + screenX + ", Y: " + screenY
				+ ". Button " + button + " was pressed.");
		// This is where the menu's/ Screens will change based on what code is written.
		if (button == 0) {
			if (MainMenuScreen.getItemSelected() == 0)
				game.switchScreens("Save Selection");
			if (MainMenuScreen.getItemSelected() == 1){
				game.switchScreens("Settings");
			}
			if (MainMenuScreen.getItemSelected() == 2)
				System.out.println("Extras Clicked");
			if (MainMenuScreen.getItemSelected() == 3)
				game.exit();
		}

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
		// Sets Item Selected Based on if its in bounds of where the text is drawn.
		if (screenX >= 419 && screenX <= 606) {
			if (screenY >= 220 && screenY < 253)
				MainMenuScreen.setItemSelected(0);
			if (screenY >= 260 && screenY <= 285 && screenX >= 431 && screenX <= 589 )
				MainMenuScreen.setItemSelected(1);
			if (screenY >= 290 && screenY <= 319 && screenX >= 439 && screenX <= 583)
				MainMenuScreen.setItemSelected(2);
			if (screenY >= 326 && screenY <= 363 && screenX >= 473 && screenX <= 547)
				MainMenuScreen.setItemSelected(3);
		}
		// If it falls out of bounds it sets the value to -1 which means nothings selected.
		if (screenX < 420 || screenX > 606)
			MainMenuScreen.setItemSelected(-1);
		if (screenY < 220 || screenY > 363)
			MainMenuScreen.setItemSelected(-1);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}