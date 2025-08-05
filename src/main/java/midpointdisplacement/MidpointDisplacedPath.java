package midpointdisplacement;

import compositecurve.CompositeBezierCurvedPath;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

/** Path2D with functionality to generate displaced lines from points */
public class MidpointDisplacedPath extends Path2D.Double {
    public static final int STRAIGHT_EDGED = 0;
    public static final int COMPOSITE_BEZIER_CURVE = 1;

    private int edgeConnectionType;
    private MidpointDisplacement midpointDisplacement;
    private Random rng;

    public MidpointDisplacedPath(int steps, int maximumDisplacement, double roughness, int seed, int edgeConnectionType) {
        this(new MidpointDisplacement(steps, maximumDisplacement, roughness), edgeConnectionType, seed);
    }

    public MidpointDisplacedPath(MidpointDisplacement midpointDisplacement, int edgeConnectionType, int rngSeed) {
        if (midpointDisplacement == null | edgeConnectionType > 1) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        this.midpointDisplacement = midpointDisplacement;
        this.edgeConnectionType = edgeConnectionType;
        this.rng = new Random(rngSeed);
    }

    /** Adds points to the path by drawing lines from current point to the displaced midpoints */
    public void midpointDisplacedLineTo(double x, double y){
        Point2D from = getCurrentPoint();
        Point2D to = new Point2D.Double(x, y);
        List<Point2D> points = midpointDisplacement.generateMidpoints(from, to, rng.nextInt());

        if (edgeConnectionType == STRAIGHT_EDGED) {
            for (Point2D p : points) {
                lineTo(p.getX(), p.getY());
            }
        }
        else {
            CompositeBezierCurvedPath compositeCurves = new CompositeBezierCurvedPath(points);
            append(compositeCurves, true);
        }
    }
}