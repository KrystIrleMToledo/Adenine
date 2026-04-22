package PD8TOTAL;

import javax.swing.JOptionPane;

/**
 * OOP CONCEPTS:
 * 1. INHERITANCE: MedicalKit extends ClinicItem.
 * 2. ENCAPSULATION: private field 'healthBoost' with public access logic.
 * 3. OVERRIDING: triggerEffect() is redefined for the specific item.
 */
class ClinicItem {
    protected String name; 
   
    public ClinicItem(String name) {
        this.name = name;
    }

    public void triggerEffect() {
        System.out.println("Item interacted with.");
    }
}

class MedicalKit extends ClinicItem {
    private int healthBoost; 

    public MedicalKit(int boost) {
        super("Medical Kit"); 
        this.healthBoost = boost;
    }

    @Override 
    public void triggerEffect() {
        JOptionPane.showMessageDialog(null, 
            "MISSION COMPLETE!\nYou found the " + name + ".\nHealth increased by " + healthBoost + " points!",
            "Clinic Storage Objective", JOptionPane.INFORMATION_MESSAGE);
    }
}
/** * --- GAME DOCUMENTATION & OBJECTIVES ---
 * * 1. MOVING CHARACTER:
 * Character moves across a 7x9 grid using JLayeredPane. The map background 
 * is rendered on the Default Layer, while the character sprites are 
 * swapped between JLabels on the Palette Layer.
 * * 2. COLLISION DETECTION:
 * A list of 'blockedTiles' is randomly generated at start. The 'isBlocked()' 
 * check ensures the character cannot pass through these indices, simulating 
 * walls or storage equipment obstacles.
 * * 3. MAP OBJECTIVE:
 * The player must locate a hidden 'medKitPosition'. Stepping on this secret 
 * tile triggers an event from the ClinicItem OOP class, boosting health.
 * * 4. OOP CONCEPTS USED:
 * - ENCAPSULATION: The health value is private within the MedicalKit class.
 * - INHERITANCE: MedicalKit inherits 'name' and properties from ClinicItem.
 * - OVERRIDING: triggerEffect() is overridden to provide the mission popup.
 */