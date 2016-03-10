package watkinsalgo;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import watkinsalgo.Geometry.Surface;
import watkinsalgo.util.DoublePair;

/**
 *
 * @author Евгений
 */
public class Executer {

    
    private final Canvas canvas;
    private GraphicsContext context;
    
    private ArrayList<Surface> surfaces = new ArrayList<>();
    private EventsList list = new EventsList();
    
    public Executer(Canvas canvas) {
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();
    }
    private ArrayList<Surface> drawnSurfaces = new ArrayList<>();
    public void drawPicture() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        DoublePair border = findMinAndMaxY();
        
        
        for (int currentY = (int)border.getMaxValue(), endY = (int)border.getMinValue(); currentY >= endY; currentY--) {
            //добавление-удаление рассматриваемых плоскостей
            changeDrawnSurfacesList(currentY);
            //поиск всех точек, пересекающих эту плоскость, и составление из них отрезков
            //поиск точек пересечения между этими отрезками
            //сортировка
            //отрисовка
            
        }
    }
    
    private void changeDrawnSurfacesList(double currentY) {
        ArrayList<SurfaceEvent> events = list.getEvent(currentY);
        if (events == null) return;
        events.stream().forEach(event ->
        {
            if (event.isStart) {
                drawnSurfaces.add(event.surface);
            } else {
                drawnSurfaces.remove(event.surface);
            }
        });
    }
    
    private DoublePair findMinAndMaxY() {
        DoublePair border = new DoublePair(Double.MAX_VALUE, Double.MIN_VALUE);
        surfaces.stream().forEach(
            current -> {
                DoublePair pair = current.getMinimumAndMaximumY();
                list.addEvent(new SurfaceEvent(current, true), pair.getMaxValue());
                list.addEvent(new SurfaceEvent(current, false), pair.getMinValue());
                border.changeValue(pair);
            }
        );
        return border;
    }
        
    class SurfaceEvent {
        Surface surface;
        boolean isStart;

        public SurfaceEvent(Surface surface, boolean isStart) {
            this.surface = surface;
            this.isStart = isStart;
        }
    }
    
    class EventsList {
        HashMap<Double, ArrayList<SurfaceEvent>> list = new HashMap<>();
        
        void addEvent(SurfaceEvent event, double y) {
            if (list.get(y) == null) {
                ArrayList<SurfaceEvent> events = new ArrayList<>();
                list.put(y, events);
            } 
            list.get(y).add(event);
        } 
        
        ArrayList<SurfaceEvent> getEvent(Double y) {
            return list.get(y);
        }
    }
}