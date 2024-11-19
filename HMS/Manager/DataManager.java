package HMS.Manager;

import java.util.List;

/**
 * The DataManager interface defines methods that all managers (such as
 * MedicineManager and PatientManager) must implement for handling entities.
 */
public interface DataManager<T> {
    void loadData();  // Load data (e.g., from a file)
    void saveData();  // Save data (e.g., to a file)
    void addOrUpdateData(T item);  // Add or update an item
    List<T> getData();  // Get all items
}