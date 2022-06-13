package seq;

import lib.Body;
import lib.Boundary;
import lib.P2d;
import lib.V2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {

    private final List<Body> bodies;
    private List<ModelObserver> observers;
    private Boundary bounds;

    private long iter;

    /* virtual time */
    private double vt = 0;

    /* virtual time step */
    double dt = 0.001;

    public Model(final int nBodies, final Boundary bounds){
        //Generazione dei bodies
        this.bounds = bounds;
        Random rand = new Random(System.currentTimeMillis());
        bodies = new ArrayList<>();
        for (int i = 0; i < nBodies; i++) {
            double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
            double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
            Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
            bodies.add(b);
        }

        this.observers = new ArrayList<>();
    }

    public void update(long iter){

        this.iter = iter;

        //Aggiorna i bodies

        for (int i = 0; i < bodies.size(); i++) {
            Body b = bodies.get(i);

            /* compute total force on bodies */
            V2d totalForce = computeTotalForceOnBody(b);

            /* compute instant acceleration */
            V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

            /* update velocity */
            b.updateVelocity(acc, dt);
        }

        /* compute bodies new pos */

        for (Body b : bodies) {
            b.updatePos(dt);
        }

        /* check collisions with boundaries */

        for (Body b : bodies) {
            b.checkAndSolveBoundaryCollision(bounds);
        }

        vt = vt + dt;

        //Notifica gli observer
        notifyObservers();
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
                } catch (Exception ex) {
                }
            }
        }

        /* add friction force */
        totalForce.sum(b.getCurrentFrictionForce());

        return totalForce;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public Boundary getBounds() {
        return bounds;
    }

    public long getIter() {
        return iter;
    }

    public double getVt() {
        return vt;
    }

    public void addObserver(ModelObserver obs){
        observers.add(obs);
    }

    private void notifyObservers(){
        for (ModelObserver obs: observers){
            obs.modelUpdated(this);
        }
    }
}
