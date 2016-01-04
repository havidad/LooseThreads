package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.entities.Player;
import com.hypoxiagames.marioclone.util.FPSCounter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements com.badlogic.gdx.Screen {
	private final MainGame game;
	Assets assetManager;
	
	private Player player;
	public static final float UNITSCALE  = 1/32f;
	// Variables for Text to be drawn;
	private BitmapFont fpsFont;
	GlyphLayout glyphLayout;
	
	private TiledMap testMap;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private MapObjects groundObjects;
	private OrthographicCamera camera;

	private ProjectileManager projManager;
	private CollisionManager colManager;
	
	private int tileHeight = 32;
	private int tileWidth = 32;
	
	// Used to keep things drawn at the same size regardless of the screen size.
	public final static float GAME_WORLD_WIDTH = 100;
	public final static float GAME_WORLD_HEIGHT = 100;
	public final float ASPECT_RATIO;
	
	public GameScreen(final MainGame gam){
		game = gam;
		glyphLayout = new GlyphLayout();
		
		ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;
		
		Parameters params = new Parameters();
		params.flipY = true;
		testMap = new TmxMapLoader().load("Maps/testRoom.tmx", params);
		layer = (TiledMapTileLayer)testMap.getLayers().get(1);
		renderer =  new OrthogonalTiledMapRenderer(testMap, UNITSCALE);
		camera = new OrthographicCamera();
		
		// Sets the camera to show the maximum game world size, times an aspect ratio so it looks similar at most
		//resolutions.
		camera.setToOrtho(false, 24, 24);
		
		player = new Player(new Sprite(new Texture("Sprites/imgo.png")), testMap, this);
		
		// Spawns him somewhere in the room.
		player.setPosition(1 ,1);
		Gdx.input.setInputProcessor(player);
		
		glyphLayout = new GlyphLayout();
		fpsFont = Assets.getManager().get("Fonts/DroidSans.fnt");
		
		/*
		 * 	Handle Projectiles being shot from the player. This
		 * is just an initialization of this system.
		*/
		projManager = new ProjectileManager(player.getLocation(), this);
		
		colManager = new CollisionManager(this, testMap);
		
		
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		projManager.findBulletSpawn();
		FPSCounter.isShown = true;
		player.update(delta);
		renderer.setView(camera);
		renderer.render();
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		FPSCounter.ShowCounter(fpsFont, renderer.getBatch(), glyphLayout);
		renderer.getBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width * UNITSCALE;
		camera.viewportHeight = height * UNITSCALE;
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

	// When writing code, make sure to check the Declarations of every thing you are working with. If they 
	//extend or 
	@Override
	public void dispose() {
		testMap.dispose();
		renderer.dispose();
		
	}

	// Get/Setters
	public TiledMap getTestMap() {
		return testMap;
	}

	public void setTestMap(TiledMap testMap) {
		this.testMap = testMap;
	}

	public MapObjects getObjects(MapObjects objects) {
		return objects;
	}
	
	public MainGame getMainGame(){
		return game;
	}


	public TiledMapTileLayer getLayer() {
		return layer;
	}


	public void setLayer(TiledMapTileLayer layer) {
		this.layer = layer;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}


	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

}
