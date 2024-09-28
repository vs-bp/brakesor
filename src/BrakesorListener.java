
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;

// Listener class to interface with the simulation itself.
// This is what actually performs the computations for the airbrakes.
class BrakesorListener extends AbstractSimulationListener {
    // Don't do anything special at the start of the simulation.
    @Override public void startSimulation(SimulationStatus status) throws SimulationException {}
    // After every step, check if the altitude is above the deployment threshold.
    // If so, then add in a default fin to the bottom stage.
    @Override public void postStep(SimulationStatus status) throws SimulationException {
        // TODO Does nothing for now.
    }
}