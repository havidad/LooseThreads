package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.input.TitleInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class GameScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	final TitleInput inputProcessor;
	Assets assetManager;
	SpriteBatch batch;
	OrthographicCamera camera;
	GlyphLayout glyphLayout;
	
	public GameScreen(final MainGame gam){
		game = gam;
		inputProcessor = new TitleInput(game); //TODO Implement a control page for the game screen.
		glyphLayout = new GlyphLayout();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
				
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}

}
