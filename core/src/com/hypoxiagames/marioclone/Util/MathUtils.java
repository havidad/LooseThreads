package com.hypoxiagames.marioclone.Util;

public class MathUtils {
	public static float abs(float n) {
		return (n >= 0.0f) ? n : -n;
	}

	public static float sgn(float n) {
		if (n > 0.0f)
			return 1.0f;
		else if (n < 0.0f)
			return -1.0f;
		else
			return 0.0f;
	}

	public static float min(float a, float b) {
		return (a < b) ? a : b;
	}

	public static float max(float a, float b) {
		return (a > b) ? a : b;
	}

	public static int min(int a, int b) {
		return (a < b) ? a : b;
	}

	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}
}
