package com.hypoxiagames.marioclone.Util;


import com.badlogic.gdx.math.Rectangle;
import static com.hypoxiagames.marioclone.Util.MathUtils.*;

public class Rectangles {

	private Rectangles () {
	}

	public static void setRectangle (Rectangle r, float x, float y, float w, float h) {
		r.x = x;
		r.y = y;
		r.width = w;
		r.height = h;
	}

	public static Rectangle union (Rectangle a, Rectangle b, Rectangle result) {
		result.x = min(a.x, b.x);
		result.y = min(a.y, b.y);
		result.width = max(a.x + a.width, b.x + b.width) - result.x;
		result.height = max(a.y + a.height, b.y + b.height) - result.y;
		return result;
	}
}