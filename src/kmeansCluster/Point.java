package kmeansCluster;

public class Point {
	private int x = 0, y = 0;

	public Point(int xVal, int yVal) {
		// TODO Create a new point at x(xVal, yVal)
		this.x = xVal;
		this.y = yVal;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void printPoint() {
		System.out.print(this.getX() + "\t" + this.getY() + "\t");
	}

	public static double distance(Point p, Point centroid) {
		return Math.sqrt(Math.pow((centroid.getX() - p.getX()), 2) + Math.pow((centroid.getY() - p.getY()), 2));
	}
}
