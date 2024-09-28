
import net.sf.openrocket.simulation.extension.AbstractSimulationExtension;
import net.sf.openrocket.rocketcomponent.FlightConfiguration;
import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.simulation.FlightEvent;
import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.FlightEvent.Type;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;

public class Brakesor extends AbstractSimulationExtension {
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

    /* ---------------------- Property Getters and Setters ---------------------- */
    // Returns the stored finset name for the configurator
    // to recall on display.
    public String getFinsetName() { 
        return config.getString("FinsetName", "AIRBRAKES"); 
    }
    // Edits the stored finset name when the configurator
    // gets input.
    public void setFinsetName(String name) {
        config.put("FinsetName", name);
        fireChangeEvent();
    }
    
    // Accesses deploy altitude from the configurator.
    // Note, config is inherited from AbstractSimulationExtension.
    public void setDeployAltitude(double altitude) { 
        config.put("DeployAltitude", altitude);
        fireChangeEvent();
    }
    public double getDeployAltitude() { 
        return config.getDouble("DeployAltitude", 1500.0); 
    }

    /* -------------------------------- Listener -------------------------------- */
    // Listener class to interface with the simulation itself.
    // This is what actually performs the computations for the airbrakes.
    class BrakesorListener extends AbstractSimulationListener {
        private boolean brake_deployed;
        private FlightConfiguration deployed_configuration;
    
        // Returns the rocket component who'se name is exactly finset name given the
        // flight configuration.
        private RocketComponent findAirbrakes(FlightConfiguration configuration) throws SimulationException {
            // Loop through every active component, and find the one with the same name as 
            // our user specified name field.
            RocketComponent found_finset = null;
			for (RocketComponent c : configuration.getActiveComponents()) {
                // If the component's name equals the name we're looking for, store it and break.
				if (c.getName().equals(getFinsetName())) {
					found_finset = (RocketComponent) c; break;
		        }
			}
            // We didn't find the finset, throw an error.
			if (found_finset == null) {
				throw new SimulationException(
                    "Please ensure there's an object with a name of exactly " + 
                    getFinsetName() + 
                    ", as it was was not found."
                );
			}
            // Return what we found.
            return found_finset;
        }

        // At the start of the simulation, remember what the brake's settings are, and 
        // what it's parent component is. Then delete them.
        // ! Note, finset must be trapezoidal.
        @Override public void startSimulation(SimulationStatus status) throws SimulationException {
            // Find and override the airbrakes CD to be 0.
            var configuration = status.getConfiguration();
            var brakes = findAirbrakes(configuration);
            brakes.setOverrideCD(0.0);
            brakes.setCDOverridden(true);
            status.setConfiguration(configuration);
            // Set the brake to not be deployed.
            brake_deployed = false;
        }
        // After every simulation step, check if the altitude is above the deployment threshold.
        // If so, then add in a default fin to the bottom stage.
        @Override public void postStep(SimulationStatus status) throws SimulationException {
            // Code to end the simulation in case it's needeed
            // status.getEventQueue().add(new FlightEvent(FlightEvent.Type.SIMULATION_END, status.getSimulationTime(), null));

            // Check if past the deployment altitude.
            // If we are, re-add the finset we removed, and remember to 
            // stop checking (Set deployed bool to true).
            if(!brake_deployed) {
                if(status.getRocketWorldPosition().getAltitude() > getDeployAltitude()) { 
                    // Find the airbrakes and set their CD to be recalculated.
                    var configuration = status.getConfiguration();
                    var brakes = findAirbrakes(configuration);
                    brakes.setCDOverridden(false);
                    status.setConfiguration(configuration);
                    // Also push a recovery event.
                    status.getFlightData().addEvent(new FlightEvent(
                        Type.RECOVERY_DEVICE_DEPLOYMENT, status.getSimulationTime())
                    );
                    // Now remember to stop redeploying the airbrakes.
                    brake_deployed = true;
                }
            }
        }
    }
}