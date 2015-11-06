package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.input.SaveSelectionInput;
import com.hypoxiagames.marioclone.screens.SaveSelectionScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;

public class SaveSelectionScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	final SaveSelectionInput inputProcessor;
	public SaveSelectionScreen(final MainGame gam) {
		game = gam;
		inputProcessor = new SaveSelectionInput(game);
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputProcessor);
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}

}
