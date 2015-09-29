/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Alex Ras
 */
public class GenerateRight implements Runnable, Observer{
    private KochFractal koch;
    private KochManager km = new KochManager(null);
    
    public GenerateRight(KochFractal koch){
        this.koch = koch;
        this.koch.addObserver(this);
    }
    @Override
    synchronized public void run() {
        koch.generateRightEdge();
        km.IncreaseCount();
        if(km.count == 3){
            notify();
        }
    }

    @Override
    synchronized public void update(Observable o, Object arg) {
        km.edges.add((Edge)arg);
        
    }
    
}
