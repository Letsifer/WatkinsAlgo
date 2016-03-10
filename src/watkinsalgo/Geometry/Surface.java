/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watkinsalgo.Geometry;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import watkinsalgo.util.DoublePair;

/**
 *
 * @author Евгений
 */
public class Surface {
    private ArrayList<Segment> edges = new ArrayList<>();
    private final Color surfaceColor;
    
    /**
     * Находит наименьшее и наибольшее значени Y у плоскости.
     * @return 
     */
    public DoublePair getMinimumAndMaximumY() {
        DoublePair answer = new DoublePair(Double.MAX_VALUE, Double.MIN_VALUE);
        edges.stream().forEach(current -> answer.changeValue(current.getMinimumAndMaximumY()));
        return answer;
    }
    
    /**
     * Нахождение точки или отрезка пересечения данной плоскости с плоскостью Y=c.
     * Если только одна точка пересечения, то обе границы одинаковы.
     * @param currentY
     * @return 
     */
    public Segment getIntersectionWithY(double currentY) {
        Point start = null, finish = null;
        for (Segment edge : edges) {
            Point[] intersection = edge.getIntersectionWithY(currentY);
            if (intersection != null) {
                if (intersection.length == 2) {
                    start = intersection[0];
                    finish = intersection[1];
                    break;
                }
                if (start == null) {
                    start = intersection[0];
                } else {
                    finish = intersection[0];
                    break;
                }
            }
        }
        if (finish == null) {
            return new Segment(start, start);
        }
        return new Segment(start, finish);
    }

    public Surface(Color surfaceColor) {
        this.surfaceColor = surfaceColor;
    }
    
    public void addEdge(Point a, Point b) {
        edges.add(new Segment(a, b));
    }

    public Color getSurfaceColor() {
        return surfaceColor;
    }
    
    public int getEdgesNumber() {
        return edges.size();
    }
}
