package com.hypoxiagames.loosethreads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hypoxiagames.loosethreads.*;
import com.hypoxiagames.loosethreads.input.SaveSelectionInput;
import com.hypoxiagames.loosethreads.screens.SaveSelectionScreen;

public class SaveSelectionScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	final SaveSelectionInput inputProcessor;
	Assets assets;
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont mainFont;
	GlyphLayout glyphLayout;
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
		game.screenX = Gdx.graphics.getWidth();
		game.screenY = Gdx.graphics.getHeight();
		
		
		
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
