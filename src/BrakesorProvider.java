import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtensionProvider;

@Plugin
public class BrakesorProvider extends AbstractSimulationExtensionProvider {
    // Override parent constructor to show in extensions list.
    public BrakesorProvider() {
        super(Brakesor.class, "Airbrakes", "NSL Airbrakes");
    }
}