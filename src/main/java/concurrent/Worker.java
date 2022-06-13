package concurrent;

import lib.Body;
import lib.Boundary;
import lib.V2d;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Worker extends Thread{

    private int start;
    private int nBodiesForWorker;
    private List<Body> bodies;
    private CyclicBarrier readyToStart;
    private CyclicBarrier readyToComputeNewPositions;
    private CyclicBarrier readyToDisplay;
    private double dt;
    private Boundary bounds;

    public Worker(int start, int nBodiesForWorker, List<Body> bodies, double dt, Boundary bounds, CyclicBarrier readyToStart, CyclicBarrier readyToComputeNewPositions, CyclicBarrier readyToDisplay) {
        this.start = start;
        this.nBodiesForWorker = nBodiesForWorker;
        this.bodies = bodies;
        this.readyToStart = readyToStart;
        this.readyToComputeNewPositions = readyToComputeNewPositions;
        this.readyToDisplay = readyToDisplay;
        this.dt = dt;
        this.bounds = bounds;
    }

    public void run(){
        try{
            while(true){

                //System.out.println("[WORKER] waiting to start");
                readyToStart.await();

                //System.out.println("[WORKER] updating velocities");
                // update bodies velocities
                for(int i=start; i<start+nBodiesForWorker; i++){
                    if (i < bodies.size()){
                        Body b = bodies.get(i);
                        /* compute total force on bodies */
                        V2d totalForce = computeTotalForceOnBody(b);
                        /* compute instant acceleration */
                        V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
                        /* update velocity */
                        b.updateVelocity(acc, dt);
                    }
                }

                readyToComputeNewPositions.await();

                //System.out.println("[WORKER] updating positions");
                // update bodies positions
                for(int i=start; i<start+nBodiesForWorker; i++){
                    if (i < bodies.size()){
                        Body b = bodies.get(i);
                        /* compute bodies new pos */
                        b.updatePos(dt);
                        /* check collisions with boundaries */
                        b.checkAndSolveBoundaryCollision(bounds);
                    }
                }

                readyToDisplay.await();
            }
        } catch (Exception exception) { }
    }

    private V2d computeTotalForceOnBody(Body b) {

        V2d totalForce = new V2d(0, 0);

        /* compute total repulsive force */

        for (int j = 0; j < bodies.size(); j++) {
            Body otherBody = bodies.get(j);
            if (!b.equals(otherBody)) {
                try {
                    V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
                    totalForce.sum(forceByOtherBody);
                } catch (Exception ex) { }
            }
        }

        /* add friction force */
        totalForce.sum(b.getCurrentFrictionForce());

        return totalForce;
    }

}
