
import java.util.HashMap;

public class UC3 {

    // ================= ROOM DOMAIN MODEL =================
    static abstract class Room {
        private String roomType;
        private int beds;
        private double price;

        public Room(String roomType, int beds, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.price = price;
        }

        public String getRoomType() {
            return roomType;
        }

        public int getBeds() {
            return beds;
        }

        public double getPrice() {
            return price;
        }

        public abstract void displayRoomDetails();
    }

    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 1000.0);
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 2000.0);
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 5000.0);
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
        }
    }

    // ================= INVENTORY CLASS =================
    static class RoomInventory {

        // HashMap → Single Source of Truth
        private HashMap<String, Integer> inventory;

        // Constructor → Initialize inventory
        public RoomInventory() {
            inventory = new HashMap<>();

            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        // Get Availability (O(1))
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Update Availability (Controlled)
        public void updateAvailability(String roomType, int newCount) {
            inventory.put(roomType, newCount);
        }

        // Display Inventory
        public void displayInventory() {
            System.out.println("---- Current Inventory ----");
            for (String roomType : inventory.keySet()) {
                System.out.println(roomType + " : " + inventory.get(roomType));
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 3.1\n");

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Create Room Objects (Domain)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display Room Types
        System.out.println("---- Room Types ----");
        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        suite.displayRoomDetails();

        // Display Inventory (Centralized)
        System.out.println();
        inventory.displayInventory();

        // Example Update
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single Room", 4);

        // Display Updated Inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nSystem running with centralized inventory.");
    }
}