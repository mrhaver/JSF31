/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.Observable;
import java.util.Observer;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Frank Haver
 */
public class GenerateRight implements Observer, Runnable{
    
    final private KochFractal koch;
    final private KochManager km;
    private JSF31KochFractalFX application;
    private TimeStamp tsb;
    
    public GenerateRight(KochManager km, JSF31KochFractalFX application, int level, TimeStamp tsb){
        this.koch = new KochFractal();
        koch.addObserver(this);
        koch.setLevel(level);
        this.km = km;
        this.application = application;
        this.tsb = tsb;
    }
    
    @Override
    synchronized public void run() {
        koch.generateRightEdge();
        km.IncreaseCounter();
        if(km.getCounter() == 3){
            application.requestDrawEdges();
            km.setCounter(0);
            tsb.setEnd("Eind Berekenen");
            application.setTextCalc(tsb.toString());
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        km.voegEdgeToe((Edge)arg);        
    }
    
}
