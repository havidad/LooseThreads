package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.input.PlayerInput;

public class Player extends Sprite {

	// Player movement velocity 
	private Vector2 velocity = new Vector2(0,0);
	
	// Change these values to change different parameters for the characters movement in the world
	private float speed = 60 * 2, gravity = 60 * 1.0f;
	
	// Used to see if a player can land on this a secific tile
	private TiledMapTileLayer collisionLayer;
	
	public Vector2 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite);
		this.collisionLayer = collisionLayer;
	}
	@Override
	public void draw(Batch spriteBatch){
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	public void update(float delta){
		// Applying gravity
		velocity.y -= gravity * delta;
		
		// Limits the player to only going too fast.
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;
		
		// Save old position
		float oldX = getX(), oldY = getY(); 
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean xCollided = false, yCollided = false;
		
		//Move on X Axis
		setX(getX() + velocity.x * delta);
		
		if(velocity.x < 0){
			// Check the top left tile in relation to the player for collision
			xCollided = collisionLayer.getCell((int)(getX()/tileWidth),(int)(getY()+getHeight() / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			//Check middle left for collision
			if(!xCollided)
				xCollided = collisionLayer.getCell((int)(getX()/tileWidth),(int)((getY() + getHeight()/2)/tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			// Check Bottom left for collision
			if(!xCollided)
				xCollided = collisionLayer.getCell((int) (getX()/tileWidth),(int)(getY()/tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
		} else if (velocity.x > 0){
			//Check Top Right for collision
			xCollided = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth),(int)(getY() + getHeight() / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			// Check Middle Right for collision
			if(!xCollided)
				xCollided = collisionLayer.getCell((int)((getX() + getWidth())/ tileWidth),(int)((getY() + getHeight() /2 ) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			//Check Bottom Right for collision
			if(!xCollided)
				xCollided = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth), (int)(getY() / tileHeight))
				.getTile().getProperties().containsKey("blocked");
		}
		// Stop moving for xcollision
		if(xCollided){
			setX(oldX);
			velocity.x = 0;
		}
		
		//Move on Y Axis
		setY(getY() + velocity.y * delta);
		
		if(velocity.y < 0){
			// Check collision Bottom Left
			yCollided = collisionLayer.getCell((int)(getX() / tileWidth),(int)(getY()/ tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			// Check collision Bottom Middle
			if(!yCollided)
				yCollided = collisionLayer.getCell((int)((getX() + getWidth() / 2)/ tileWidth),(int)(getY()/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
			
			// Check collision Bottom Right
			if(!yCollided)
				yCollided = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth),(int)(getY()/ tileHeight))
				.getTile().getProperties().containsKey("blocked");
		} else if (velocity.y > 0){
			// Top Left
			yCollided = collisionLayer.getCell((int)(getX() / tileWidth),(int)((getY() + getHeight()) / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
			// Top Middle
			if(!yCollided)
				yCollided = collisionLayer.getCell((int)((getX() + getWidth() / 2)/ tileWidth),(int)((getY() + getHeight()) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
			
			//Top Right
			// Check collision Bottom Right
			if(!yCollided)
				yCollided = collisionLayer.getCell((int)((getX() + getWidth()) / tileWidth),(int)((getY() + getHeight()) / tileHeight))
				.getTile().getProperties().containsKey("blocked");
		}
		
		// Stop moving Y collision
		if(yCollided){
			setY(oldY);
			velocity.y = 0;
		}
		
	}
	
}
