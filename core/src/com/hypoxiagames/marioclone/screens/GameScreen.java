package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.entities.Player;
import com.hypoxiagames.marioclone.input.PlayerInput;
import com.hypoxiagames.marioclone.input.TitleInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class GameScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	final PlayerInput playerInput;
	Assets assetManager;
	SpriteBatch batch;
	GlyphLayout glyphLayout;
	
	private Player player;
	
	private TiledMap testMap;
	private OrthogonalTiledMapRenderer renderer;
	private StaticTiledMapTile staticTile;
	private OrthographicCamera camera;
	
	final float GAME_WORLD_WIDTH = 480, GAME_WORLD_HEIGHT = 360;
	final float ASPECT_RATIO;
	
	public GameScreen(final MainGame gam){
		game = gam;
		playerInput = new PlayerInput(game);
		Gdx.input.setInputProcessor(playerInput);
		glyphLayout = new GlyphLayout();
		
		ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;
	
		testMap = new TmxMapLoader().load("Maps/TestMap.tmx");
		renderer =  new OrthogonalTiledMapRenderer(testMap);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT);
		
		player = new Player(new Sprite(new Texture("Sprites/Finn.png")), (TiledMapTileLayer)testMap.getLayers().get(0));
		player.setPosition(2 * player.getCollisionLayer().getTileWidth(), 71 * player.getCollisionLayer().getTileHeight());
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		if(PlayerInput.upKeyDown == true)
			System.out.println("Up is pressed down");
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
		
	}

	@Override
	public void pause() {
				
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		testMap.dispose();
		renderer.dispose();
		
	}

}
