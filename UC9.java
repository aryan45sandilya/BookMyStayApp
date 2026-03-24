
import java.util.*;

public class UC9 {

    // ================= CUSTOM EXCEPTION =================
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // ================= INVENTORY =================
    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 0);
        }

        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, -1);
        }

        public void decrement(String roomType) throws InvalidBookingException {

            int current = inventory.getOrDefault(roomType, -1);

            // Validation: prevent invalid room type
            if (current == -1) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            // Validation: prevent negative inventory
            if (current <= 0) {
                throw new InvalidBookingException("No rooms available for: " + roomType);
            }

            inventory.put(roomType, current - 1);
        }

        public void displayInventory() {
            System.out.println("\n---- Current Inventory ----");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }
        }
    }

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

    // ================= VALIDATOR =================
    static class BookingValidator {

        public static void validate(Reservation r, RoomInventory inventory)
                throws InvalidBookingException {

            // Validate null or empty input
            if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            if (r.getRoomType() == null || r.getRoomType().isEmpty()) {
                throw new InvalidBookingException("Room type cannot be empty.");
            }

            // Validate room type exists
            if (inventory.getAvailability(r.getRoomType()) == -1) {
                throw new InvalidBookingException("Selected room type does not exist.");
            }
        }
    }

    // ================= BOOKING SERVICE =================
    static class BookingService {

        private RoomInventory inventory;

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public void processBooking(Reservation r) {

            try {
                // Step 1: Validate input (Fail-Fast)
                BookingValidator.validate(r, inventory);

                // Step 2: Allocate room (may throw exception)
                inventory.decrement(r.getRoomType());

                // Success
                System.out.println("Booking Confirmed for " + r.getGuestName() +
                        " (" + r.getRoomType() + ")");

            } catch (InvalidBookingException e) {

                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test Cases (Valid + Invalid)

        Reservation r1 = new Reservation("Aryan", "Single Room");   // valid
        Reservation r2 = new Reservation("", "Double Room");        // invalid name
        Reservation r3 = new Reservation("Rahul", "Luxury Room");   // invalid type
        Reservation r4 = new Reservation("Priya", "Suite Room");    // no availability

        service.processBooking(r1);
        service.processBooking(r2);
        service.processBooking(r3);
        service.processBooking(r4);

        // Final inventory state
        inventory.displayInventory();

        System.out.println("\nSystem remains stable after handling errors.");
    }
}