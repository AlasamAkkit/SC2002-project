package HMS.Staff;

/**
 * Represents a common interface for staff menus in the hospital management system.
 * This interface ensures that all staff types implement a method to display their specific menu.
 */
public interface StaffMenu {

    /**
     * Displays the menu specific to the staff type implementing this interface.
     * This method should provide the list of options available for the staff member to interact with the system.
     */
    void displayMenu();
}
