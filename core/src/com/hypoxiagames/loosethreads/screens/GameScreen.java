package com.hypoxiagames.loosethreads.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
	final MainGame game;
	Assets assetManager;

	private Player player;
	private TextureAtlas bloopAnim;
	public static final float UNITSCALE = 1 / 64f;

	GlyphLayout glyphLayout;

	private int level = 0;

	// List of all maps in the game;
	private TiledMap testMap;
	private TiledMap overWorld; // The Main Outside world of the game.
	private TiledMap currentMap;

	private TiledMapTileLayer layer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private ProjectileManager projManager;

	private Music music;

	// Used to keep things drawn at the same size regardless of the screen size.
	public final static float GAME_WORLD_WIDTH = 100;
	public final static float GAME_WORLD_HEIGHT = 75;
	public final float ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;

	// BitmapFont fpsFont;
	float fps;

	boolean isIndoors;

	public GameScreen(final MainGame gam) {
		game = gam;

	}

	@Override
	public void show() {
		glyphLayout = new GlyphLayout();

		music = Assets.getManager().get("Sounds/Home.mp3");

		// Flips the Y axis on the map to match LibGdx's, then defines the map
		// and makes a layer off of that.
		// That layer will eventually become collision, and moved from here, as
		// it gets refactored into a map
		// loader system.
		Parameters params = new Parameters();
		params.flipY = true;
		testMap = new TmxMapLoader().load("Maps/area1/newtilesroomwip.tmx", params);
		overWorld = new TmxMapLoader().load("Maps/overworld/overworld.tmx", params);
		currentMap = testMap;
		layer = (TiledMapTileLayer) currentMap.getLayers().get(1);
		renderer = new OrthogonalTiledMapRenderer(currentMap, UNITSCALE);
		camera = new OrthographicCamera(GAME_WORLD_WIDTH * UNITSCALE, GAME_WORLD_HEIGHT * UNITSCALE);

		// Sets the camera to show the maximum game world size, times an aspect
		// ratio so it looks similar at most
		// resolutions.
		camera.setToOrtho(false, 24, 24);

		bloopAnim = new TextureAtlas("Sprites/SpriteSheets/BleepBloop.pack");
		player = new Player(bloopAnim, new Sprite(), testMap, this);

		// Spawns him somewhere in the room.
		player.getSprite().setPosition(18, 57);
		Gdx.input.setInputProcessor(player);

		glyphLayout = new GlyphLayout();
		/*
		 * Handle Projectiles being shot from the player. This is just an
		 * initialization of this system.
		 */
		projManager = new ProjectileManager(player.getLocation(), this);
		music.setVolume(0.33f);
		music.setLooping(true);
		music.play();
	}

	@Override
	public void render(float delta) {
		if (level == 0)
			isIndoors = true;
		if (level == 1)
			isIndoors = false;
		// Clear the screen from the last frame
		if (!isIndoors)
			Gdx.gl.glClearColor(0, 0, 205, 1);
		else
			Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fps = Gdx.graphics.getFramesPerSecond();
		System.out.println("FPS: " + fps);

		// Update the player movement and collision
		player.update(delta);

		// Determine where the bullet should spawn this frame step, if it were
		// to fire this frame.
		projManager.findBulletSpawn();

		// Have the camera follow the player around, based on the player's new
		// location.
		camera.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
		camera.update();

		// Render the map using the camera
		renderer.setView(camera);
		renderer.render();

		// Render the player, bullets, and anything else that moves/lives in
		// this part.
		renderer.getBatch().begin();
		player.updateAnimation(delta);
		renderer.getBatch().draw(player.getCurrentFrame(), player.getSprite().getX(), player.getSprite().getY(),
				player.getSprite().getWidth(), player.getSprite().getHeight());
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
		music.stop();
		dispose();

	}

	// When writing code, make sure to check the Declarations of every thing you
	// are working with. If they
	// extend or
	@Override
	public void dispose() {
		testMap.dispose();
		bloopAnim.dispose();
		music.dispose();
		player.getAnimationTexture().dispose();
		// fpsFont.dispose();

	}

	public void switchScreen(String screen) {
		music.stop();
		game.switchScreens(screen);
	}

	public void moveCamera(float x, float y) {
		camera.position.set(camera.viewportWidth / 2 + x, camera.viewportHeight / 2, 0);
		camera.update();
	}

	public void movePlayerToRoom(Vector2 newLocation) {
		player.getSprite().setX(newLocation.x);
		player.getSprite().setY(newLocation.y);
	}

	public void switchMaps(int index) {
		switch (index) {
		case 0:
			currentMap = testMap;
			level = index;
			break;
		case 1:
			currentMap = overWorld;
			level = index;
			break;
		}
		layer = (TiledMapTileLayer) currentMap.getLayers().get(1);
		renderer.setMap(currentMap);
		player.colManager.switchMap(currentMap);
	}

	public int getLevel() {
		return level;
	}

	// Get/Setters
	public TiledMap getMap(int index) {
		switch (index) {
		case -1:
			return currentMap;
		case 0:
			return testMap;
		case 1:
			return overWorld;
		}
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

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

}
