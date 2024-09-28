
import net.sf.openrocket.simulation.extension.AbstractSimulationExtension;

import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.exception.SimulationException;
 /**
  * Simulation extension that launches a rocket from a specific altitude.
  */
 public class Brakesor extends AbstractSimulationExtension {
    // Stored variables.
    //
    // Occasionally recalled for display by the 
    // configurator using some getters, or overwritten
    // by the configurator using setters.
    //
    private String finset_name;

    // Add a listener to the simulation.
    public void initialize(SimulationConditions conditions) throws SimulationException {
        conditions.getSimulationListenerList().add(new BrakesorListener());
    }

    // Overrides for showing extension name and description in the
    // simulation's extension list.
    @Override public String getName() { 
        return "NSL Airbrakes"; 
    }
    @Override public String getDescription() {
         return "Simple extension for simulating airbrakes on UCF's 2024 NSL rocket.";
    }

    // Returns the stored finset name for the configurator
    // to recall on display.
    public String getFinsetName() { return finset_name; }
    // Edits the stored finset name when the configurator
    // gets input.
    public void setFinsetName(String name) { finset_name = name; }
}