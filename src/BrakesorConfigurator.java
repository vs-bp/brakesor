import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.gui.SpinnerEditor;
import net.sf.openrocket.gui.adaptors.DoubleModel;
import net.sf.openrocket.gui.components.BasicSlider;
import net.sf.openrocket.gui.components.UnitSelector;
import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import net.sf.openrocket.unit.UnitGroup;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JSpinner;

@Plugin
public class BrakesorConfigurator extends AbstractSwingSimulationExtensionConfigurator<Brakesor> {
    // References to related objects.
    private JPanel panel;
    private Brakesor extension;

    // TODO Not sure what this does exactly, just saw it in the example repo.
    public BrakesorConfigurator() {
		super(Brakesor.class);
	}

    // Actual UI handling function.
    @Override public JComponent getConfigurationComponent(Brakesor extension, Simulation simulation, JPanel panel) {
        // Set the external references for this object.
        this.extension = extension;
        this.panel = panel;

        panel.add(new JLabel("Representative Fin set EXACT Name:"));
        
        // Finset name input.
        JTextField finSetName = new JTextField();
        // Recall the name from the extension.
		finSetName.setText(extension.getFinsetName());
        // If the textbox gets focus, overwrite the name the extension
        // is storing.
        finSetName.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {}
            @Override public void focusLost(FocusEvent e) {
                extension.setFinsetName(finSetName.getText());
            }
        });
        // Strange keywords for text input sizing
		panel.add(finSetName, "growx, span3, wrap");

        // Rows for input floats.
        addRow("Deployment Altitude: ", "DeployAltitude", UnitGroup.UNITS_DISTANCE, 0.0, 10000.0);


        return panel;
    }

    // Adds a unit selector, label, slider input, and text input.
    private void addRow(String prompt, String fieldName, UnitGroup units, double min, double max) {
		panel.add(new JLabel(prompt + ":"));
		
		DoubleModel m = new DoubleModel(extension, fieldName, units, min, max);
		
		JSpinner spin = new JSpinner(m.getSpinnerModel());
		spin.setEditor(new SpinnerEditor(spin));
		panel.add(spin, "w 65lp!");
		
		UnitSelector unit = new UnitSelector(m);
		panel.add(unit, "w 25");
		
		BasicSlider slider = new BasicSlider(m.getSliderModel(0, 1000));
		panel.add(slider, "w 75lp, wrap");

	}	
}
