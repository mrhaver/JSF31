/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private Monitor monitor;

    public BallRunnable(Ball ball, Monitor monitor) {
        this.ball = ball;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if(ball.getColor() == Color.RED && ball.isEnteringCs()){
                    monitor.enterReader();
                }
                else if(ball.getColor() == Color.RED && ball.isLeavingCs()){
                    monitor.exitReader();
                }
                else if(ball.getColor() == Color.BLUE && ball.isEnteringCs()){
                    monitor.enterWriter();
                }
                else if(ball.getColor() == Color.BLUE && ball.isLeavingCs()){
                    monitor.exitWriter();
                }
                ball.move();
                
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
                if(ball.getXPos() == ball.getMinCsX() && ball.getColor() == Color.BLUE){
                    monitor.decreaseWritersWaiting();
                }
                if(ball.getXPos() <= ball.getMaxCsX() && ball.getXPos() > ball.getMinCsX()){
                    if(ball.getColor() == Color.BLUE){
                        try {
                            monitor.exitWriter();
                            
                        } catch (InterruptedException ex1) {
                            System.out.println(ex1.toString());
                        }
                    }
                    if(ball.getColor() == Color.RED){
                        try {
                            monitor.exitReader();
                        } catch (InterruptedException ex1) {
                            System.out.println(ex1.toString());
                        }
                    }
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
