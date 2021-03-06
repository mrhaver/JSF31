/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Frank Haver
 */
public class GenerateRight implements Observer,Callable{
    
    final private KochFractal koch;
    final private KochManager km;
    private JSF31KochFractalFX application;
    private ArrayList<Edge> edges;
    private TimeStamp tsb;
    private CyclicBarrier cb;
    
    public GenerateRight(KochManager km, JSF31KochFractalFX application, int level, TimeStamp tsb, CyclicBarrier cb){
        this.cb = cb;
        this.koch = new KochFractal();
        koch.addObserver(this);
        koch.setLevel(level);
        this.km = km;
        this.application = application;
        this.tsb = tsb;
        this.edges = new ArrayList<>();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge) arg);    
    }

    @Override
    public Object call() throws Exception {
        koch.generateRightEdge();
        cb.await();
        return edges;
    }
    
}
