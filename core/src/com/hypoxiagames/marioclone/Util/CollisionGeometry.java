package com.hypoxiagames.marioclone.Util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static com.hypoxiagames.marioclone.Util.Rectangles.*;

public class CollisionGeometry {
	private final Array<Rectangle> runs;
	private final Rectangle r;

	/** Creates this collision geometry.
	 * @param runs the model-space rectangles that make up this object's collision geometry. */
	public CollisionGeometry (Array<Rectangle> runs) {
		this.runs = runs;
		r = new Rectangle();
	}

	/** Tests if this collision geometry would collide with a rectangle if it was at the given coordinates.
	 * @param other the rectangle to test against.
	 * @param x the x coordinate of this collision geometry.
	 * @param y the y coordinate of this collision geometry.
	 * @return true if in collision otherwise false. */
	public boolean intersects (Rectangle other, float x, float y) {
		boolean result = false;
		for (int i = 0; i < runs.size; i++) {
			Rectangle run = runs.get(i);
			setRectangle(r, x + run.x, y + run.y, run.width, run.height);
			if (Colliders.intersects(r, other)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/** Tests if this collision geometry is in collision with another.
	 * @param x this collision geometry's x coordinate.
	 * @param y this collision geometry's y coordinate.
	 * @param other the other collision geometry.
	 * @param otherX the other collision geometry's x coordinate.
	 * @param otherY the other collision geometry's y coordinate.
	 * @return true if in collision otherwise false. */
	public boolean intersects (float x, float y, CollisionGeometry other, float otherX, float otherY) {
		boolean result = false;
		for (int i = 0; i < runs.size; i++) {
			Rectangle run = runs.get(i);
			setRectangle(r, x + run.x, y + run.y, run.width, run.height);
			if (other.intersects(r, otherX, otherY)) {
				result = true;
				break;
			}
		}
		return result;
	}
}