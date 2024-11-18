package HMS.Pharmacist;

/**
 * Represents a medication with its name, stock levels, and thresholds for low stock alerts.
 */
public class Medication {
    private String medicationName;
    private int stockLevel;
    private int lowStockThreshold;

    /**
     * Constructs a new Medication object.
     *
     * @param medicationName      The name of the medication.
     * @param initialStock        The initial stock level of the medication.
     * @param lowStockThreshold   The threshold below which the stock is considered low.
     */
    public Medication(String medicationName, int initialStock, int lowStockThreshold) {
        this.medicationName = medicationName;
        this.stockLevel = initialStock;
        this.lowStockThreshold = lowStockThreshold;
    }

    /**
     * Returns the name of the medication.
     *
     * @return The medication name.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Returns the current stock level of the medication.
     *
     * @return The current stock level.
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Returns the low stock threshold of the medication.
     *
     * @return The low stock threshold.
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    /**
     * Sets the stock level of the medication.
     *
     * @param newStockLevel The new stock level to set.
     */
    public void setStockLevel(int newStockLevel) {
        this.stockLevel = newStockLevel;
        System.out.println("Stock level for " + medicationName + " set to " + newStockLevel);
    }

    /**
     * Sets the low stock threshold.
     *
     * @param newThreshold The new low stock threshold to set.
     */
    public void setLowStockThreshold(int newThreshold) {
        this.lowStockThreshold = newThreshold;
        System.out.println("Low stock threshold for " + medicationName + " set to " + newThreshold);
    }

    /**
     * Replenishes the stock of the medication.
     *
     * @param amount The amount by which to increase the stock.
     */
    public void replenishStock(int amount) {
        this.stockLevel += amount;
        System.out.println(medicationName + " stock replenished by " + amount + ". Total stock: " + stockLevel);
    }

    /**
     * Consumes a specified amount of medication from the stock.
     *
     * @param amount The amount to consume.
     */
    public void consumeStock(int amount) {
        if (amount > stockLevel) {
            System.out.println("Not enough stock available to dispense.");
        } else {
            this.stockLevel -= amount;
            System.out.println(medicationName + " stock reduced by " + amount + ". Total stock: " + stockLevel);
        }
    }

    /**
     * Checks if the stock level is below the low stock threshold.
     *
     * @return true if stock is below threshold, false otherwise.
     */
    public boolean isBelowThreshold() {
        return stockLevel <= lowStockThreshold;
    }

    /**
     * Dispenses one unit of medication, reducing the stock by 1.
     */
    public void dispense() {
        consumeStock(1);
    }

    /**
     * Replenishes the medication stock by a specified quantity.
     *
     * @param quantity The quantity to add to the stock.
     */
    public void replenish(int quantity) {
        this.stockLevel += quantity;
    }
}


