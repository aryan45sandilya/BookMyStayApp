
import java.io.*;
import java.util.*;

public class UC12 {

    // ================= RESERVATION =================
    static class Reservation implements Serializable {
        private static final long serialVersionUID = 1L;

        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public void display() {
            System.out.println("Reservation: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room: " + roomType);
        }
    }

    // ================= SYSTEM STATE =================
    static class SystemState implements Serializable {
        private static final long serialVersionUID = 1L;

        HashMap<String, Integer> inventory;
        List<Reservation> history;

        public SystemState(HashMap<String, Integer> inventory, List<Reservation> history) {
            this.inventory = inventory;
            this.history = history;
        }
    }

    // ================= PERSISTENCE SERVICE =================
    static class PersistenceService {

        private static final String FILE_NAME = "system_state.dat";

        // Save state
        public void save(SystemState state) {

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                out.writeObject(state);
                System.out.println("System state saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }

        // Load state
        public SystemState load() {

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("System state loaded successfully.");
                return (SystemState) in.readObject();

            } catch (FileNotFoundException e) {

                System.out.println("No previous data found. Starting fresh.");

            } catch (Exception e) {

                System.out.println("Error loading data. Starting with safe defaults.");
            }

            return null;
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 12.0\n");

        PersistenceService service = new PersistenceService();

        // Try loading previous state
        SystemState state = service.load();

        HashMap<String, Integer> inventory;
        List<Reservation> history;

        if (state != null) {
            inventory = state.inventory;
            history = state.history;
        } else {
            // Initialize default state
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            history = new ArrayList<>();
        }

        // Simulate operations
        history.add(new Reservation("R001", "Aryan", "Single Room"));
        history.add(new Reservation("R002", "Rahul", "Double Room"));

        inventory.put("Single Room", inventory.get("Single Room") - 1);

        // Display current state
        System.out.println("---- Current Bookings ----");
        for (Reservation r : history) {
            r.display();
        }

        System.out.println("\n---- Current Inventory ----");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, history);
        service.save(newState);

        System.out.println("\nSystem ready for restart with recovered state.");
    }
}