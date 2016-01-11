package com.hypoxiagames.loosethreads.input;

import com.badlogic.gdx.Input.Keys;
import com.hypoxiagames.loosethreads.MainGame;
import com.hypoxiagames.loosethreads.screens.*;

public class TitleInput implements com.badlogic.gdx.InputProcessor{
	final MainGame game;
	public static boolean upButtonPressed, downButtonPressed, leftButtonPressed, rightButtonPressed;

	public TitleInput(final MainGame gam) {
		game = gam;
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
				System.out.println("Settings Pressed");
				game.switchScreens("Settings");
				break;
			case 2:
				System.out.println("Extras Pressed");
				break;
			case 3:
				System.out.println("Exit Pressed");
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
		switch(keycode){
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
		
		 System.out.println("Touched at X: " + screenX + ", Y: " + screenY
				+ ". Button " + button + " was pressed.");
		 /*
		 This is where the menu's/ Screens will change based on what code is written.
		if (button == 0) {
			if (TitleScreen.getItemSelected() == 0)
				game.switchScreens("Save Selection");
			if (TitleScreen.getItemSelected() == 1){
				game.switchScreens("Settings");
			}
			if (TitleScreen.getItemSelected() == 2)
				System.out.println("Extras Clicked");
			if (TitleScreen.getItemSelected() == 3)
				game.exit();
		}*/

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
		/*// Sets Item Selected Based on if its in bounds of where the text is drawn.
		if (screenX >= 419 && screenX <= 606) {
			if (screenY >= 220 && screenY < 253)
				TitleScreen.setItemSelected(0);
			if (screenY >= 260 && screenY <= 285 && screenX >= 431 && screenX <= 589 )
				TitleScreen.setItemSelected(1);
			if (screenY >= 290 && screenY <= 319 && screenX >= 439 && screenX <= 583)
				TitleScreen.setItemSelected(2);
			if (screenY >= 326 && screenY <= 363 && screenX >= 473 && screenX <= 547)
				TitleScreen.setItemSelected(3);
		}
		// If it falls out of bounds it sets the value to -1 which means nothings selected.
		if (screenX < 420 || screenX > 606)
			TitleScreen.setItemSelected(-1);
		if (screenY < 220 || screenY > 363)
			TitleScreen.setItemSelected(-1);*/
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}