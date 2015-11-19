package com.hypoxiagames.marioclone.screens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hypoxiagames.marioclone.Assets;
import com.hypoxiagames.marioclone.MainGame;
import com.hypoxiagames.marioclone.input.TitleInput;
import com.hypoxiagames.marioclone.Util.*;

public class TitleScreenNew implements com.badlogic.gdx.Screen {
	final MainGame game;
	final TitleInput input;
	Sprite backgroundSprite;
	Sprite finn;
	SpriteBatch batch;
	Viewport viewport;
	OrthographicCamera camera;
	BitmapFont titleFont, menuItemFont;
	GlyphLayout glyphLayout;
	Stage stage;
	final float GAME_WORLD_WIDTH = 480, GAME_WORLD_HEIGHT = 360;
	final float ASPECT_RATIO;
	
	// Keeps track of how many frames have passed.
	long frame;
	
	
	// Used for logic dealing with selecting an item from the menu. Will be unimplemented when someone
	//gets around to implementing buttons as menu items.
	private static int itemSelected;
	private static boolean isKeyPressed;
	
	// Strings to be displayed on the screen
		public static final String TITLE = "Project: Mafia";
		public static final String menuItem[] = { "Play Now", "Settings",
				"Credits", "Exit" };
	
	public TitleScreenNew(final MainGame gam){
		game = gam;
		
		
		ASPECT_RATIO = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		
		input = new TitleInput(game);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new StretchViewport(GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		loadAssets();
		
		// To fill the whole screen with an image, multiply by aspect ratio(for a static unmoving background,
				//To have a scrollale level, make it as big as the game world.
		backgroundSprite.setSize(GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT);
		finn.setSize(30, 48);
		finn.setPosition(200,25);
		
		glyphLayout = new GlyphLayout();
		
		Gdx.input.setInputProcessor(input);
		
		frame = 0;
		
	}
	@Override
	public void show() {
		
		
	}
	
	private void processControl(float delta){
		// Update delta
		float lerp = 0.1f;
		Vector3 position = camera.position;
		
		if(frame % 5 == 0){
			if (TitleInput.upButtonPressed == true){
				setItemSelected(getItemSelected() - 1);
				System.out.println("Up is pressed down");
				if(getItemSelected() < 0)
					setItemSelected(3);
			}
			if(TitleInput.downButtonPressed == true){	
				setItemSelected(getItemSelected() + 1);
				if(getItemSelected() > 3)
					setItemSelected(0);
			}
			if(TitleInput.rightButtonPressed == true){
				float finnXBefore = finn.getX();
				finn.setX(finnXBefore += 700 * delta);
				System.out.println(String.valueOf(finnXBefore));
				if(finn.getX() >= GAME_WORLD_WIDTH * ASPECT_RATIO - finn.getWidth()){
					finn.setX(GAME_WORLD_WIDTH * ASPECT_RATIO - finn.getWidth());
				}
				//if(finn.getX()  camera.viewportWidth)
					//camera.position.x += 665 * delta;
				if(camera.position.x >= camera.viewportWidth)
					camera.position.x = camera.viewportWidth;
			}
			if(TitleInput.leftButtonPressed == true){
				float finnXBefore = finn.getX();
				if(finn.getX() <= -10)
					finn.setX(-10);
				else
					finn.setX(finnXBefore -= 700 * delta);
				
				if(finn.getX() < 100)
					camera.position.x -= 690 * delta;
				if(camera.position.x <= camera.viewportWidth/2)
					camera.position.x = camera.viewportWidth/2;
			}
		}
	}

	@Override
	public void render(float delta) {
		frame++;
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		camera.update();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		backgroundSprite.draw(batch);
		finn.draw(batch);
		glyphLayout.setText(titleFont, TITLE);
		titleFont.draw(batch, TITLE, 0, 0);
		ShowFPSCounter.isShown = true;
		drawMenuItems();
		ShowFPSCounter.ShowCounter(menuItemFont, batch, glyphLayout);
		batch.end();
		
		processControl(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		dispose();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	public static int getItemSelected() {
		return itemSelected;
	}

	public static void setItemSelected(int number) {
		itemSelected = number;
	}
	
	public static boolean getKeyPressed(){
		return isKeyPressed;
	}
	
	public static void setIsKeyPressed(boolean isPressed)
	{
		isKeyPressed = isPressed;
	}
	
	
	public void drawMenuItems() {
		float yDraw = (int) (GAME_WORLD_HEIGHT) * ASPECT_RATIO ;

		for(int i = 0; i < 4; i++)
		{ 
			if(i == itemSelected){
				menuItemFont.setColor(Color.RED);
				menuItemFont.getData().setScale(0.7f);
			}
			else{
				menuItemFont.setColor(Color.WHITE);
				menuItemFont.getData().setScale(0.5f);
			}
			glyphLayout.setText(menuItemFont, menuItem[i]);
			menuItemFont.draw(batch, glyphLayout, (GAME_WORLD_WIDTH * ASPECT_RATIO / 2)
					- glyphLayout.width / 2, yDraw);
			yDraw -= 30;
		}
	}
	
	// Loads Assets based on what assets are needed for the scene.
		public void loadAssets() {
			Assets.load();
			while (!Assets.getManager().update()) {
					System.out.println(Assets.getManager().getProgress() * 100 + "%");
			}
			System.out.println(Assets.getManager().getProgress() * 100 + "% All Files Loaded");
			if (Assets.getManager().isLoaded("Screens/Background.png")) {
				backgroundSprite = new Sprite(new Texture(Gdx.files.internal("Screens/Background.png")));
				finn = new Sprite(new Texture(Gdx.files.internal("Sprites/Finn.png")));
				titleFont = Assets.getManager().get("Fonts/AVidaNova.fnt");
				menuItemFont = Assets.getManager().get("Fonts/DroidSans.fnt");

			} else {
				loadAssets();
			}
		}

}
