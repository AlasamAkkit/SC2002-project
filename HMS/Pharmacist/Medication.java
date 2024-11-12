package HMS.Pharmacist;

public class Medication {
    private String medicationName;
    private int stockLevel;
    private int lowStockThreshold;

    // Constructor
    public Medication(String medicationName, int initialStock, int lowStockThreshold) {
        this.medicationName = medicationName;
        this.stockLevel = initialStock;
        this.lowStockThreshold = lowStockThreshold;
    }

    // Getters and setters
    public String getMedicationName() {
        return medicationName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setStockLevel(int newStockLevel) {
        this.stockLevel = newStockLevel;
        System.out.println("Stock level for " + medicationName + " set to " + newStockLevel);
    }

    public void setLowStockThreshold(int newThreshold) {
        this.lowStockThreshold = newThreshold;
        System.out.println("Low stock threshold for " + medicationName + " set to " + newThreshold);
    }

    public void replenishStock(int amount) {
        this.stockLevel += amount;
        System.out.println(medicationName + " stock replenished by " + amount + ". Total stock: " + stockLevel);
    }

    public void consumeStock(int amount) {
        if (amount > stockLevel) {
            System.out.println("Not enough stock available to dispense.");
        } else {
            this.stockLevel -= amount;
            System.out.println(medicationName + " stock reduced by " + amount + ". Total stock: " + stockLevel);
        }
    }

    public boolean isBelowThreshold() {
        return stockLevel <= lowStockThreshold;
    }

    // New dispense method to reduce stock by 1 unit
    public void dispense() {
        consumeStock(1);
    }
}


