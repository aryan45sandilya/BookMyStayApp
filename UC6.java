
import java.util.*;

public class UC6 {

    // ================= RESERVATION =================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ================= INVENTORY =================
    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        public void decrement(String roomType) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }

        public void displayInventory() {
            System.out.println("\n---- Current Inventory ----");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }
        }
    }

    // ================= BOOKING SERVICE =================
    static class BookingService {

        private Queue<Reservation> queue;
        private RoomInventory inventory;

        // Track allocated room IDs
        private Set<String> allocatedRoomIds;

        // Map room type → assigned room IDs
        private HashMap<String, Set<String>> allocationMap;

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
            this.queue = new LinkedList<>();
            this.allocatedRoomIds = new HashSet<>();
            this.allocationMap = new HashMap<>();
        }

        // Add request
        public void addRequest(Reservation r) {
            queue.add(r);
        }

        // Generate unique Room ID
        private String generateRoomId(String roomType) {
            String base = roomType.replace(" ", "").toUpperCase();

            int counter = 1;
            String roomId;

            do {
                roomId = base + "_" + counter;
                counter++;
            } while (allocatedRoomIds.contains(roomId));

            return roomId;
        }

        // Process Queue (FIFO)
        public void processBookings() {

            System.out.println("\n---- Processing Bookings ----");

            while (!queue.isEmpty()) {

                Reservation r = queue.poll(); // FIFO
                String type = r.getRoomType();

                int available = inventory.getAvailability(type);

                if (available > 0) {

                    // Generate unique ID
                    String roomId = generateRoomId(type);

                    // Add to set (Uniqueness)
                    allocatedRoomIds.add(roomId);

                    // Map room type → IDs
                    allocationMap.putIfAbsent(type, new HashSet<>());
                    allocationMap.get(type).add(roomId);

                    // Atomic update (allocation + inventory)
                    inventory.decrement(type);

                    // Confirm booking
                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Assigned Room ID: " + roomId + "\n");

                } else {
                    System.out.println("Booking Failed (No Availability)");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type + "\n");
                }
            }
        }

        // Display allocations
        public void displayAllocations() {
            System.out.println("\n---- Allocated Rooms ----");

            for (String type : allocationMap.keySet()) {
                System.out.println(type + " → " + allocationMap.get(type));
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 6.0\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Booking service
        BookingService service = new BookingService(inventory);

        // Add booking requests (FIFO)
        service.addRequest(new Reservation("Aryan", "Single Room"));
        service.addRequest(new Reservation("Rahul", "Single Room"));
        service.addRequest(new Reservation("Priya", "Single Room")); // should fail
        service.addRequest(new Reservation("Neha", "Double Room"));
        service.addRequest(new Reservation("Karan", "Suite Room"));

        // Process bookings
        service.processBookings();

        // Display results
        service.displayAllocations();
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely.");
    }
}