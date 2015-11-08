package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.input.TitleInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class TitleScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	final TitleInput inputProcessor;
	Assets assets;
	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont titleFont, menuItemFont;
	GlyphLayout glyphLayout;

	// Starting at 0 for the top item selected, used to locate which item is
	// selected on the main menu
	private static int itemSelected;

	public Texture background;

	// Strings to be displayed on the screen
	public static final String TITLE = "Project: Mafia";
	public static final String menuItem[] = { "Play Now", "Settings",
			"Credits", "Exit" };
	
	// Constructor used as the create method.
	public TitleScreen(final MainGame gam) {
		game = gam;
		inputProcessor = new TitleInput(game);
		glyphLayout = new GlyphLayout();
	}
	public static int getItemSelected() {
		return itemSelected;
	}

	public static void setItemSelected(int number) {
		itemSelected = number;
	}

	public void drawMenuItems() {
		int yDraw = 500;

		for(int i = 0; i < 4; i++)
		{ 
			if(i == itemSelected){
				menuItemFont.setColor(Color.RED);
				menuItemFont.getData().setScale(1.3f);
			}
			else{
				menuItemFont.setColor(Color.WHITE);
				menuItemFont.getData().setScale(1);
			}
			glyphLayout.setText(menuItemFont, menuItem[i]);
			menuItemFont.draw(batch, glyphLayout, (game.screenX / 2)
					- glyphLayout.width / 2, yDraw);
			yDraw -= 40;
		}
		menuItemFont.setColor(Color.WHITE);
		menuItemFont.getData().setScale(0.85f);
		// FPS Counter TODO Implement FPS counter to be togglable from any screen any time.
		//For now it is just resting here.
		int fps = Gdx.graphics.getFramesPerSecond();
		glyphLayout.setText(menuItemFont , String.valueOf(fps));
		menuItemFont.draw(batch, glyphLayout, 10, 30);
	}

	public void loadAssets() {
		Assets.load();
		while (!Assets.getManager().update()) {
			System.out.println(Assets.getManager().getProgress() * 100 + "%");
		}
		if (Assets.getManager().isLoaded("Screens/mainMenuBackground.png")) {
			background = Assets.getManager().get(
					"Screens/mainMenuBackground.png");
			titleFont = Assets.getManager().get("Fonts/AVidaNova.fnt");
			menuItemFont = Assets.getManager().get("Fonts/DroidSans.fnt");

		} else {
			loadAssets();
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0, game.screenX, game.screenY);
		// Draws the Title Centered halfway in the screen, and offset from the
		// top.
		glyphLayout.setText(titleFont, TITLE);
		titleFont.draw(batch, TITLE,
				((game.screenX / 2) - glyphLayout.width / 2),
				game.screenY - 40);
		drawMenuItems();
		menuItemFont.setColor(Color.YELLOW);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		game.screenX = Gdx.graphics.getWidth();
		game.screenY = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(480, 320);
		camera.setToOrtho(false, game.screenX, game.screenY);
		Gdx.input.setInputProcessor(inputProcessor);
		batch = new SpriteBatch();
		loadAssets();
	}

	
	public void hide() {
		Gdx.input.setInputProcessor(null);
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		Assets.dispose();
		batch.dispose();
	}
}
