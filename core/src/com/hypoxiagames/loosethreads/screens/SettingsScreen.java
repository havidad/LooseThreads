package com.hypoxiagames.loosethreads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.loosethreads.Assets;
import com.hypoxiagames.loosethreads.MainGame;
import com.hypoxiagames.loosethreads.input.SettingInput;


public class SettingsScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	public final SettingInput inputProcessor;
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont font;
	Vector2 fontLoc;
	
	
	private int itemSelected;
	
	public SettingsScreen(final MainGame gam){
		game = gam;
		inputProcessor = new SettingInput(game);		
	}
	void checkInput(){
		if(inputProcessor.isLeftPressed)
			fontLoc.x -= 1;
	}
	
	public int getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(int number) {
		itemSelected = number;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		
		checkInput();
		
		batch.begin();
		font.draw(batch, "Back To Main Menu", fontLoc.x, fontLoc.y);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(game.screenX, game.screenY);
		camera.setToOrtho(false, game.screenX, game.screenY);
		fontLoc = new Vector2(500,500);
		Assets.load();
		while (!Assets.getManager().update()) {
			System.out.println(Assets.getManager().getProgress() * 100 + "%");
		}
		if (Assets.getManager().isLoaded("Fonts/DroidSans.fnt")) {
		font = Assets.getManager().get("Fonts/DroidSans.fnt");
		}
		else{
			System.out.println("Didnt load font");
		}
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		Assets.dispose();
		
	}

}
