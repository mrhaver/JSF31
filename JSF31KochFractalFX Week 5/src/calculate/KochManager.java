/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Frank Haver
 */
public class KochManager implements Observer{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    public static ArrayList<Edge> edges;
    public static int count = 0;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        koch = new KochFractal();
        koch.addObserver(this);
        edges = new ArrayList<>();
    }
    
    /**
     *
     * @param nxt
     */
    synchronized public void changeLevel(int nxt) {
            count = 0;
            koch.setLevel(nxt);
            edges.clear();
            TimeStamp tsb = new TimeStamp();
            tsb.setBegin("Begin Berekenen");
            Thread thrdleft = new Thread(new GenerateLeft(koch, this));
            Thread thrdright = new Thread(new GenerateRight(koch, this));
            Thread thrdbottom = new Thread(new GenerateBottom(koch, this));
            thrdleft.start();
            thrdright.start();
            thrdbottom.start();
            tsb.setEnd("Fractal berekend");
            application.setTextCalc(tsb.toString());
            application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
    }
    
    public void drawEdges() {
        application.clearKochPanel();
        TimeStamp tst = new TimeStamp();
        tst.setBegin("Begin Tekenen");
        for(Edge e : edges){
            application.drawEdge(e);
        }
        tst.setEnd("Fractal Getekend");
        application.setTextDraw(tst.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        //application.drawEdge((Edge)arg);
        //edges.add((Edge)arg);
    }
    
    synchronized public void IncreaseCount(){
        count++;
    }


}
