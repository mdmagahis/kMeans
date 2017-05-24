package kmeansCluster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Kmeans {

	public static void main(String[] args) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		int k = 0;
		if (args.length > 0) {
			try {
				k = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Argument " + args[0] + " must be an integer.");
				System.exit(1);
			}
		}
		
		// Take X-value for outputX.txt from inputX.txt
		String file = args[1];
		char num = file.charAt(5);
		String fileNum = Character.toString(num);
		int fileNo = Integer.parseInt(fileNum);
		
		String fileName = "output" + fileNo + ".txt";
		
		// Print output 
		PrintStream out = new PrintStream(new FileOutputStream(fileName)); 
		System.setOut(out);

		Point[] points = init(args);

		int xMin = 100000;
		int xMax = 0;
		int yMin = 100000;
		int yMax = 0;
		// Set min and max values for x and y
		for (Point point : points) {
			if (xMin > point.getX())
				xMin = point.getX();
			if (xMax < point.getX())
				xMax = point.getX();
			if (yMin > point.getY())
				yMin = point.getY();
			if (yMax < point.getY())
				yMax = point.getY();
		}

		// Make k random new centroids and initialize k clusters
		Cluster[] clusters = new Cluster[k];
		for (int i = 0; i < (k); i++) {
			int randX = ThreadLocalRandom.current().nextInt(xMin, xMax + 1);
			int randY = ThreadLocalRandom.current().nextInt(yMin, yMax + 1);
			Point iCentroid = new Point(randX, randY);
			clusters[i] = new Cluster((i));
			clusters[i].setCentroid(iCentroid);
		}

		boolean finished = false;

		while (!finished) {
			List<Point> oldCentroids = getCentroids(clusters, k);
			for (Cluster c : clusters) {
				c.clear();
			}
			clusters[0].clear();
			// Assign each point to the closest centroid (cluster)
			double minEDist = Double.MAX_VALUE;
			int clstr = 0;
			double distance = 0.0;

			for (Point point : points) {
				minEDist = Double.MAX_VALUE;
				for (int m = 0; m < k; m++) {
					distance = Point.distance(point, clusters[m].getCentroid());
					if (distance < minEDist) {
						minEDist = distance;
						clstr = m;
					}
				}
				clusters[clstr].setClusterID((clstr));
				clusters[clstr].addPoint(point);
			}

			// Calculate new Centroids
			for (Cluster c : clusters) {
				int sumX = 0, sumY = 0;
				int totalP = c.getSize();
				List<Point> list = c.getClusterPoints();

				for (Point p : list) {
					sumX += p.getX();
					sumY += p.getY();
				}
				if (totalP > 0) {
					int newX = (sumX / totalP);
					int newY = (sumY / totalP);
					Point newCentroid = new Point(newX, newY);
					c.setCentroid(newCentroid);
				}
			}

			// Check if centroids moved
			List<Point> newCentroids = getCentroids(clusters, k);

			double centroidDistance = 0;
			for (int i = 0; i < oldCentroids.size(); i++) {
				centroidDistance += Point.distance(oldCentroids.get(i), newCentroids.get(i));
			}
			if (centroidDistance == 0) {
				finished = true;
			}
		}
		
		// Final output
		for (Cluster c : clusters) {
			c.showCluster();
		}

	}

	private static Point[] init(String init[]) throws FileNotFoundException {

		Scanner inFile = new Scanner(new FileReader(init[1])); 
		
		File input = new File(init[1]);
		inFile = new Scanner(new FileReader(input)); 
		
		// Store contents of inFile into List<String> tempCoors
		List<String> tempCoors = new ArrayList<String>();

		String cursor1 = "";
		while (inFile.hasNext()) {
			cursor1 = inFile.next();
			tempCoors.add(cursor1);
		}
		inFile.close();

		// Convert the List<String> to String array
		String[] strCoordinates = tempCoors.toArray(new String[0]);

		// Convert the strCoordinates to an array of integers
		int[] coordinates = new int[strCoordinates.length];

		for (int i = 0; i < strCoordinates.length; i++) {
			coordinates[i] = Integer.parseInt(strCoordinates[i]);
		}

		// create array of Point[]
		Point[] points = new Point[(coordinates.length / 2)];
		int j = 0;
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(coordinates[j], coordinates[++j]);
			j++;
		}
		return points;
	}

	private static List<Point> getCentroids(Cluster[] clusters, int k) {
		List<Point> centroids = new ArrayList<Point>(k);
		for (Cluster c : clusters) {
			Point temp = c.getCentroid();
			Point point = new Point(temp.getX(), temp.getY());
			centroids.add(point);
		}
		return centroids;
	}
}
