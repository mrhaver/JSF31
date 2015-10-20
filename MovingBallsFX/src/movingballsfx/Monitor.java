/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Alex Ras
 */
public class Monitor {
    
    private int readersActive;
    private int writersActive;
    Lock monLock = new ReentrantLock();
    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();
    private int writersWaiting;
    
    public Monitor(){
        
    }
    
    public void enterReader() throws InterruptedException{
        
        monLock.lock();
        try{
            while(writersActive > 0){
                okToRead.await();
                
            }
            readersActive++;
        }
        finally{
            monLock.unlock();
        }
    }
    
    public void exitReader() throws InterruptedException{
        monLock.lock();
        try{
            readersActive--;
            if(readersActive == 0){
                okToWrite.signal();
            }
        }
        finally{
            monLock.unlock();
        }
    }
    
    public void enterWriter() throws InterruptedException{
        
        monLock.lock();
        try{
            while(writersActive > 0 || readersActive > 0){
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        }
        finally{
            monLock.unlock();
        }
    }
    
    public void exitWriter() throws InterruptedException{
        monLock.lock();
        
        try{
            writersActive--;
            if(writersWaiting > 0){ 
                okToWrite.signal();
            }
            else{
                okToRead.signalAll();
            }
        }
        finally{
            monLock.unlock();
        }
    }
    
    public void decreaseWritersWaiting(){
        writersWaiting--;
    }
}
