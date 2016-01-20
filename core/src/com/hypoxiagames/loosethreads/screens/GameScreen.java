package com.hypoxiagames.loosethreads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.loosethreads.*;
import com.hypoxiagames.loosethreads.entities.Player;

public class GameScreen implements com.badlogic.gdx.Screen {
	private final MainGame game;
	Assets assetManager;

	private Player player;
	private TextureAtlas bloopAnim;
	public static final float UNITSCALE = 1 / 64f;

	GlyphLayout glyphLayout;

	private TiledMap testMap;
	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private ProjectileManager projManager;

	// Used to keep things drawn at the same size regardless of the screen size.
	public final static float GAME_WORLD_WIDTH = 100;
	public final static float GAME_WORLD_HEIGHT = 75;
	public final float ASPECT_RATIO;

	public GameScreen(final MainGame gam) {
		game = gam;
		glyphLayout = new GlyphLayout();

		ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;

		Parameters params = new Parameters();
		params.flipY = true;
		testMap = new TmxMapLoader().load("Maps/area1/newtilesroomwip.tmx", params);
		layer = (TiledMapTileLayer) testMap.getLayers().get(1);
		renderer = new OrthogonalTiledMapRenderer(testMap, UNITSCALE);
		camera = new OrthographicCamera(GAME_WORLD_WIDTH * UNITSCALE, GAME_WORLD_HEIGHT * UNITSCALE);

		// Sets the camera to show the maximum game world size, times an aspect
		// ratio so it looks similar at most
		// resolutions.
		camera.setToOrtho(false, 24, 24);
		
		bloopAnim = new TextureAtlas("Sprites/SpriteSheets/BleepBloop.pack");
		player = new Player(bloopAnim,new Sprite(), testMap, this);

		// Spawns him somewhere in the room.
		player.getSprite().setPosition(14, 57);
		Gdx.input.setInputProcessor(player);

		glyphLayout = new GlyphLayout();
		/*
		 * Handle Projectiles being shot from the player. This is just an
		 * initialization of this system.
		 */
		projManager = new ProjectileManager(player.getLocation(), this);

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		// Clear the screen from the last frame
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update the player movement and collision
		player.update(delta);
		
		
		// Determine where the  bullet should spawn this frame step, if it were to fire this frame.
		projManager.findBulletSpawn();
		
		// Have the camera follow the player around, based on the player's new location.
		camera.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
		camera.update();
		
		// Render the map using the camera
		renderer.setView(camera);
		renderer.render();
		
		// Render the player, bullets, and anything else that moves/lives in this part.
		renderer.getBatch().begin();
		renderer.getBatch().draw(player.getAnimationRegion(), player.getSprite().getX(), player.getSprite().getY(),player.getSprite().getWidth(), player.getSprite().getHeight());
		renderer.getBatch().end();

	}

	// When the screen resizes
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width * UNITSCALE;
		camera.viewportHeight = height * UNITSCALE;
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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

	// When writing code, make sure to check the Declarations of every thing you
	// are working with. If they
	// extend or
	@Override
	public void dispose() {
		testMap.dispose();
		renderer.dispose();

	}

	public void moveCamera(float x, float y) {
		camera.position.set(camera.viewportWidth / 2 + x, camera.viewportHeight / 2, 0);
		camera.update();
	}
	public void movePlayerToRoom(Vector2 newLocation){
		player.getSprite().setX(newLocation.x);
		player.getSprite().setY(newLocation.y);
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

	public MainGame getMainGame() {
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
