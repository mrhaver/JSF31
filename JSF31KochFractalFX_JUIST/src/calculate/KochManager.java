/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author Frank Haver
 */
public class KochManager{
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private ArrayList<Edge> edges;
    private int counter = 0;
    ExecutorService pool;
    Future<ArrayList<Edge>> bottomEdges;
    Future<ArrayList<Edge>> rightEdges;
    Future<ArrayList<Edge>> leftEdges;
    
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        edges = new ArrayList<>();
        koch = new KochFractal();
        counter = 0;
    }
    
    synchronized public void changeLevel(int nxt) throws InterruptedException, ExecutionException, BrokenBarrierException {
        final CyclicBarrier cb = new CyclicBarrier(3);
        pool = Executors.newFixedThreadPool(3);
        koch.setLevel(nxt);
        edges.clear();
        TimeStamp tsb = new TimeStamp();        
        GenerateRight genRight = new GenerateRight(this, application, nxt, tsb,cb);
        GenerateLeft genLeft = new GenerateLeft(this, application, nxt, tsb,cb);
        GenerateBottom genBottom = new GenerateBottom(this, application, nxt, tsb,cb);
        tsb.setBegin("Begin Berekenen");
        bottomEdges = pool.submit(genBottom);
        rightEdges = pool.submit(genRight);
        leftEdges = pool.submit(genLeft);
        
    }
    
    public void drawEdges() throws InterruptedException, ExecutionException {
        pool.shutdown();
        for(Edge e : bottomEdges.get()){
            edges.add(e);
        }
        
        for(Edge e : leftEdges.get()){
            edges.add(e);
        }
        
        for(Edge e : rightEdges.get()){
            edges.add(e);
        }
        System.out.println(String.valueOf(edges.size()));  
        application.setTextNrEdges(String.valueOf(koch.getNrOfEdges()));
        application.clearKochPanel();
        TimeStamp tst = new TimeStamp();
        tst.setBegin("Begin Tekenen");
        for(Edge e : edges){
            application.drawEdge(e);
        }
        tst.setEnd("Fractal Getekend");
        application.setTextDraw(tst.toString());
    }
    
    synchronized public void IncreaseCounter(){
        counter++;
    }
    
    synchronized public int getCounter(){
        return counter;
    }
    
    synchronized public void setCounter(int value){
        counter = value;
    }
    
    synchronized public void voegEdgeToe(Edge edge){
        edges.add(edge);
    }
    
    synchronized public void notifyWait(){
        notify();
    }
    


}
