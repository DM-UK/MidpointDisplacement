An implementation of a 1D Midpoint Displacement Algorithm. Similiar to the one describe [here](https://learn.64bitdragon.com/articles/computer-science/procedural-generation/midpoint-displacement-in-one-dimension).


**Constructor**
```
MidpointDisplacement(int steps, double maximumDisplacement, double roughness) 
```
**Method call**
```
public List<Point2D> generateMidpoints(Point2D lineStart, Point2D lineFinish)
```

For interfacing with Path2D see: [MidpointDisplacement](https://github.com/DM-UK/MidpointDisplacement/blob/master/src/main/java/midpointdisplacement/MidpointDisplacedPath.java)


![screenshot](https://github.com/DM-UK/MidpointDisplacement/blob/master/src/main/resources/demo.png)
