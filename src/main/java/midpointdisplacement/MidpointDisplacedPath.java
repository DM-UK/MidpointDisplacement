package midpointdisplacement;

import compositecurve.CompositeBezierCurvedPath;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Represents a path generated using the midpoint displacement algorithm.
 * This class extends {@link Path2D.Double} and allows for both straight-edged
 * and composite Bezier curved paths.
 */
public class MidpointDisplacedPath extends Path2D.Double {
    /** Constant for a straight-edged path type. */
    public static final int STRAIGHT_EDGED = 0;
    /** Constant for a composite Bezier curved path type. */
    public static final int COMPOSITE_BEZIER_CURVE = 1;

    /**
     * Constructs a MidpointDisplacedPath using the given parameters.
     *
     * @param steps               The number of iterations for midpoint displacement.
     * @param maximumDisplacement The maximum displacement allowed.
     * @param roughness           The roughness factor of the displacement.
     * @param lineStart           The starting point of the path.
     * @param lineFinish          The ending point of the path.
     * @param seed                The seed value for randomness.
     * @param edgeType            The type of edge (STRAIGHT_EDGED or COMPOSITE_BEZIER_CURVE).
     */
    public MidpointDisplacedPath(int steps, int maximumDisplacement, double roughness, Point2D lineStart, Point2D lineFinish, int seed, int edgeType) {
        this(new MidpointDisplacement(steps, maximumDisplacement, roughness), lineStart, lineFinish, seed, edgeType);
    }

    /**
     * Constructs a MidpointDisplacedPath using a {@link MidpointDisplacement} instance.
     *
     * @param midDisplace The midpoint displacement instance.
     * @param lineStart   The starting point of the path.
     * @param lineFinish  The ending point of the path.
     * @param seed        The seed value for randomness.
     * @param edgeType    The type of edge (STRAIGHT_EDGED or COMPOSITE_BEZIER_CURVE).
     */
    public MidpointDisplacedPath(MidpointDisplacement midDisplace, Point2D lineStart, Point2D lineFinish, int seed, int edgeType) {
        if (midDisplace == null || lineStart == null || lineFinish == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        List<Point2D> points = midDisplace.generateMidpoints(lineStart, lineFinish, seed);

        // Construct the path based on the specified edge type
        if (edgeType == COMPOSITE_BEZIER_CURVE) {
            calculateCurvedPath(points);
        } else {
            calculateStraightPath(points);
        }
    }

    /**
     * Constructs a straight-edged path from the generated points.
     */
    private void calculateStraightPath(List<Point2D> points) {
        Point2D firstPoint = points.getFirst();
        moveTo(firstPoint.getX(), firstPoint.getY());

        for (int i = 1; i < points.size(); i++) {
            Point2D p = points.get(i);
            lineTo(p.getX(), p.getY());
        }
    }

    /**
     * Constructs a curved path from the generated points.
     */
    private void calculateCurvedPath(List<Point2D> points) {
        CompositeBezierCurvedPath compositeCurves = new CompositeBezierCurvedPath(points);
        append(compositeCurves, true);
    }
}