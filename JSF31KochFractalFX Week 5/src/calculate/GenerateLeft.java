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

/**
 *
 * @author Alex Ras
 */
public class GenerateLeft implements Runnable, Observer{
    private KochFractal koch;
    private KochManager km;
    
    public GenerateLeft(KochFractal koch, KochManager km){
        this.koch = koch;
        this.koch.addObserver(this);
        this.km = km;
    }
    @Override
    synchronized public void run() {
        koch.generateLeftEdge();    
    }

    @Override
    synchronized public void update(Observable o, Object arg) {
        km.edges.add((Edge)arg);
        km.IncreaseCount();
        if(km.count == 3){
            km.drawEdges();
        }
    }
    
}
