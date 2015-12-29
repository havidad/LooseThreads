package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.Util.ProjectileManager;
import com.hypoxiagames.marioclone.Util.ShowFPSCounter;
import com.hypoxiagames.marioclone.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements com.badlogic.gdx.Screen {
	private final MainGame game;
	Assets assetManager;
	
	private Player player;
	
	// Variables for Text to be drawn;
	private BitmapFont fpsFont;
	GlyphLayout glyphLayout;
	
	private TiledMap testMap;
	private OrthogonalTiledMapRenderer renderer;
	private MapObjects groundObjects;
	private OrthographicCamera camera;
	private ProjectileManager projManager;
	
	// Used to keep things drawn at the same size regardless of the screen size.
	public final static float GAME_WORLD_WIDTH = 100;
	public final static float GAME_WORLD_HEIGHT = 100;
	public final float ASPECT_RATIO;
	
	public GameScreen(final MainGame gam){
		game = gam;
		glyphLayout = new GlyphLayout();
		
		ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;
	
		testMap = new TmxMapLoader().load("Maps/testRoom.tmx");
		renderer =  new OrthogonalTiledMapRenderer(testMap);
		camera = new OrthographicCamera();
		
		// Sets the camera to show the maximum game world size, times an aspect ratio so it looks similar at most
		//resolutions.
		camera.setToOrtho(false, GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT);
		
		player = new Player(new Sprite(new Texture("Sprites/imgo.png")), 
				(TiledMapTileLayer)testMap.getLayers().get("Player"));
		
		// Spawns him somewhere in the room.
		player.setPosition(512,354);
		
		//Set collision points for player
		setCollisionMap();
		
		Gdx.input.setInputProcessor(player);
		
		glyphLayout = new GlyphLayout();
		fpsFont = Assets.getManager().get("Fonts/DroidSans.fnt");
		
		/*
		 * 	Handle Projectiles being shot from the player. This
		 * is just an initialization of this system.
		*/
		projManager = new ProjectileManager(player.getLocation(), this);
		
	}

	public void setCollisionMap(){
		try{
			groundObjects = testMap.getLayers().get(1).getObjects();
			//wallObjects = testMap.getLayers().get(3).getObjects();
		}catch (ExceptionInInitializerError e){
			System.out.println("We can't load the collision Layer");
			game.exit();
		}
	}
	
	public boolean checkPlayerCollision(MapObjects objects){
		boolean collided = false;
		//boolean almostCollidingL = false, almostCollidingU = false, almostCollidingR = false,
			//	almostCollidingD = false;
		for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
			/* Code to check if you are about to collide with a wall, if you aren't,
			*then that direction of movement will be disabled in the update method, until 
			*there isn't a wall that the player couldn't have hit before. This will limit 
			*the character from getting within a certain space to the wall, however will 
			*allow him to move just fine in other directions.This is to create a gap after
			* the player had ran into the wall, only disabling movement in that direction 
			* after hitting the wall once. Once these bounding boxes aren't hit anymore by 
			* second, larger bounding box, then the player will be able to move in that direction again.
			*/
			Rectangle rect = rectangleObject.getRectangle();
			// Checks to see if the previous check had a collision
			if(collided){
				return true;
			}
				
			if(Intersector.overlaps(rect, new Rectangle(player.getX(),player.getY()
					,player.getWidth(), player.getHeight())))
					collided = true;
			else
				collided = false;
		}
		return false;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		projManager.findBulletSpawn();
		ShowFPSCounter.isShown = true;
		// Player Collision is checked here, and player gets updated.
		player.collidedWall = checkPlayerCollision(groundObjects);
		player.update(delta);
		

		renderer.setView(camera);
		renderer.render();
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		ShowFPSCounter.ShowCounter(fpsFont, renderer.getBatch(), glyphLayout);
		renderer.getBatch().end();

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

}
