package com.hypoxiagames.loosethreads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hypoxiagames.loosethreads.Assets;
import com.hypoxiagames.loosethreads.MainGame;
import com.hypoxiagames.loosethreads.input.TitleInput;

public class TitleScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	TitleInput input;
	Sprite backgroundSprite;
	SpriteBatch batch;
	Viewport viewport;
	OrthographicCamera camera;
	BitmapFont titleFont, menuItemFont;
	GlyphLayout glyphLayout;
	Stage stage;
	Music titleMusic;

	final float GAME_WORLD_WIDTH = 480, GAME_WORLD_HEIGHT = 360;
	final float ASPECT_RATIO;
	
	// Keeps track of how many frames have passed.
	long frame;
	
	
	// Used for logic dealing with selecting an item from the menu. Will be unimplemented when someone
	//gets around to implementing buttons as menu items.
	private static int itemSelected;
	private static boolean isKeyPressed;
	
	// Strings to be displayed on the screen
		public static final String TITLE = "Loose Threads";
		public static final String menuItem[] = { "Play Now", "Settings",
				"Credits", "Exit" };
		public static final String musicLoc = "Sounds/Title/cringe.mp3";
	
	public TitleScreen(final MainGame gam){
		game = gam;
		
		
		ASPECT_RATIO = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		
		input = new TitleInput(game,this);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new StretchViewport(GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
		loadAssets();
		
		// To fill the whole screen with an image, multiply by aspect ratio(for a static unmoving background,
				//To have a scrollale level, make it as big as the game world.
		backgroundSprite.setSize(GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT);
	
		
		glyphLayout = new GlyphLayout();
		
		Gdx.input.setInputProcessor(input);
		
		frame = 0;
		
		titleMusic.setLooping(true);
		titleMusic.setVolume(0.25f);
		titleMusic.play();
	}
	@Override
	public void show() {
		input = new TitleInput(game,this);
		Gdx.input.setInputProcessor(input);
	}
	
	private void processControl(float delta){
		
		// Checks 20 times in a second and goes through an array that mirrors the options on the screen.
		//this is how we are doing Menu's until some UI controls get implemented into the game
		if(frame % 6 == 0){
			if (TitleInput.upButtonPressed == true){
				setItemSelected(getItemSelected() - 1);
				if(getItemSelected() < 0)
					setItemSelected(3);
			}
			if(TitleInput.downButtonPressed == true){	
				setItemSelected(getItemSelected() + 1);
				if(getItemSelected() > 3)
					setItemSelected(0);
			}
		}
	}

	@Override
	public void render(float delta) {
		// Updates how many frames has passed. This is to limit controls on the menu, to make them actually usable
		frame++;
		
		// Needs to be called every render loop. Clears screen from last frame. 
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		// Updates camera information
		camera.update();
		
		// Begin drawing items to the screen.
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		backgroundSprite.draw(batch);
		glyphLayout.setText(titleFont, TITLE);
		titleFont.draw(batch, TITLE, GAME_WORLD_WIDTH * ASPECT_RATIO / 2 - 125 , 350);
		drawMenuItems();
		batch.end();
		
		
		
		// Calls method that processes the control of the main screen
		processControl(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		
	}

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		titleMusic.stop();
		dispose();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	public Music getTitleMusic() {
		return titleMusic;
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

		// This is a parrallel array of menu items, to draw those items on the screen.
		for(int i = 0; i < 4; i++)
		{ 
			if(i == itemSelected){
				menuItemFont.setColor(Color.GREEN);
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
			// Prints out loading info into the console. While this is happening in the future, it will slow down.
			//eventually we will need to implement an actual loading screen with a loading bar and stuff on it.
			while (!Assets.getManager().update()) {
					System.out.println(Assets.getManager().getProgress() * 100 + "%");
			}
			System.out.println(Assets.getManager().getProgress() * 100 + "% All Files Loaded");
			if (Assets.getManager().isLoaded("Screens/MainMenuBackground.png")) {
				backgroundSprite = new Sprite(new Texture(Gdx.files.internal("Screens/MainMenuBackground.png")));
				titleFont = Assets.getManager().get("Fonts/AVidaNova.fnt");
				menuItemFont = Assets.getManager().get("Fonts/DroidSans.fnt");
				titleMusic = Assets.getManager().get(musicLoc);

			} else {
				loadAssets();
			}
		}

}
