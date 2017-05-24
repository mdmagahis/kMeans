package kmeansCluster;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private int clusterID = 0;
	private Point centroid;
	public List<Point> clusterPoints;

	public Cluster(int ID) {
		// TODO Auto-generated constructor stub
		this.clusterID = ID;
		this.centroid = null;
		this.clusterPoints = new ArrayList<Point>();
	}

	public void setClusterID(int ID) {
		this.clusterID = ID;
	}

	public int getClusterID() {
		return clusterID;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void addPoint(Point point) {
		clusterPoints.add(point);
	}

	public List<Point> getClusterPoints() {
		return clusterPoints;
	}

	public int getSize() {
		return clusterPoints.size();
	}

	public void clear() {
		clusterPoints.clear();
	}

	public void showCluster() {
		for (Point point : clusterPoints) {
			point.printPoint();
			System.out.println((clusterID + 1));
		}
	}
}
