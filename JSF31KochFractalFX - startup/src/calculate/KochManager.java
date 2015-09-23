/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Frank Haver
 */
public class KochManager implements Observer{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    private TimeStamp ts;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        koch = new KochFractal();
        koch.addObserver(this);
        edges = new ArrayList<Edge>();
    }
    
    public void changeLevel(int nxt) {
        koch.setLevel(nxt);
        ts = new TimeStamp();
        ts.setBegin("Begin Meting");
        drawEdges();
        ts.setEnd("Fractal Getekend");
        application.setTextCalc(ts.toString());
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }
    
    public void drawEdges() {
        application.clearKochPanel();
        edges.clear();
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        for(Edge e : edges){
            application.drawEdge(e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge)arg);
        edges.add((Edge)arg);
    }


}
