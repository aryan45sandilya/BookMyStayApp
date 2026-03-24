
import java.util.HashMap;

public class UC4 {

    // ================= ROOM DOMAIN =================
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
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 2000.0);
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 5000.0);
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    // ================= INVENTORY =================
    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 0); // Example: unavailable
        }

        // Read-only method
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
    }

    // ================= SEARCH SERVICE =================
    static class SearchService {

        public void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {

            System.out.println("---- Available Rooms ----\n");

            for (Room room : rooms) {

                int available = inventory.getAvailability(room.getRoomType());

                // Validation: only show available rooms
                if (available > 0) {

                    room.displayRoomDetails();
                    System.out.println("Available: " + available);
                    System.out.println();
                }
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 4.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Room objects (Domain)
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search Service (Read-only)
        SearchService searchService = new SearchService();

        // Perform search
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("Search completed (No changes made to inventory).");
    }
}