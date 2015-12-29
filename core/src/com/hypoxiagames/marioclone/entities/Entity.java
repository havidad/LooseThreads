package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.math.Rectangle;
import com.hypoxiagames.marioclone.Util.Colliders;
import com.hypoxiagames.marioclone.Util.CollisionGeometry;

public class Entity {
	
	/* The default state for any new game object. */
	public static final int INACTIVE = -1;
	
	/* This game objects x coordinate in world space. */
	public float x;
	
	/* This game objects y coordinate in world space. */
	public float y;
	
	/* Height and width of this game object */
	public float width, height;
	
	/* This game objects collision geometry (if any) in local coordinates*/
	public CollisionGeometry geometry;
	
	/* Game objects current state and also how long in seconds the object has been in
	 *  its current state
	 * */
	public int state;
	
	public float stateTime;
	
	// True if object is in collision
	public boolean inCollision;
	
	//Bounding rectangle in worldspace.
	private final Rectangle bounds;
	
	public Entity(){
		stateTime = 0.0f;
		inCollision = false;
		bounds = new Rectangle();
	}
	
	/* Assigns collision geometry to this GameObject
	 * */
	public void setGeometry (CollisionGeometry geometry){
		this.geometry = geometry;
	}
	
	// Returns this bounding rectangle
	public Rectangle bounds(){
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
		return bounds;
	}
	
	// Used to put the game object into different states.
	public void setState(int state){
		this.state = state;
		stateTime = 0.0f;
	}
	
	public boolean boundsIntersect (Rectangle r) {
		return Colliders.intersects(bounds(), r);
	}

	/** Returns true if this game object's bounds intersect with the given game object.
	 * 
	 * @param go the other game object.
	 * @return true if the bounds intersect. */
	public boolean boundsIntersect (Entity go) {
		return Colliders.intersects(bounds(), go.bounds());
	}

	/** Returns true if this game object's collision geometry intersects with the given rectangle.
	 * 
	 * @param r the rectangle to intersect.
	 * @return true if the geometry intersects with the rectangle. */
	public boolean geometryIntersects (Rectangle r) {
		return geometry.intersects(r, x, y);
	}

	/** Returns true if this game object's collision geometry intersects with another game object's collision geometry.
	 * 
	 * @param go the other game object.
	 * @return true if the geometries intersect. */
	public boolean geometryIntersects (Entity go) {
		return geometry.intersects(x, y, go.geometry, go.x, go.y);
	}

	/** Returns true if this game object is in collision with a rectangle. It first does a simple box test against this game
	 * object's bounds, then, if that's true, tests its collision geometry against the rectangle.
	 * 
	 * @param r the rectangle to intersect.
	 * @return true if this game object intersects the rectangle. */
	public boolean intersects (Rectangle r) {
		return boundsIntersect(r) && (geometry == null || geometryIntersects(r));
	}

	/** Returns true if this game object is in collision with another game object. It first does a bounds test, then, if that's
	 * true, tests its collision geometry against the other game object's collision geometry. */
	public boolean intersects (Entity go) {
		if (!boundsIntersect(go)) {
			return false;
		}
		if (geometry == null) {
			return go.geometry == null || go.geometryIntersects(bounds());
		} else if (go.geometry == null) {
			return geometryIntersects(go.bounds());
		}
		return geometryIntersects(go);
	}

	/** Updates this game object. Typically you would override this to create interesting behaviour.
	 * 
	 * @param delta time in seconds since the last update. */
	public void update (float delta) {
	}
}
