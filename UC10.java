
import java.util.*;

public class UC10 {

    // ================= RESERVATION =================
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;
        private String roomId;
        private boolean isCancelled;

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
            this.isCancelled = false;
        }

        public String getReservationId() { return reservationId; }
        public String getRoomType() { return roomType; }
        public String getRoomId() { return roomId; }
        public boolean isCancelled() { return isCancelled; }

        public void cancel() {
            isCancelled = true;
        }

        public void display() {
            System.out.println("Reservation: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room: " + roomType +
                    " | Room ID: " + roomId +
                    " | Status: " + (isCancelled ? "Cancelled" : "Active"));
        }
    }

    // ================= INVENTORY =================
    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 1);
            inventory.put("Double Room", 0);
        }

        public void increment(String roomType) {
            inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
        }

        public void display() {
            System.out.println("\n---- Inventory ----");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }
        }
    }

    // ================= BOOKING HISTORY =================
    static class BookingHistory {

        private HashMap<String, Reservation> history;

        public BookingHistory() {
            history = new HashMap<>();
        }

        public void addReservation(Reservation r) {
            history.put(r.getReservationId(), r);
        }

        public Reservation getReservation(String id) {
            return history.get(id);
        }

        public void displayAll() {
            System.out.println("\n---- Booking History ----");
            for (Reservation r : history.values()) {
                r.display();
            }
        }
    }

    // ================= CANCELLATION SERVICE =================
    static class CancellationService {

        private RoomInventory inventory;
        private BookingHistory history;

        // Stack for rollback tracking (LIFO)
        private Stack<String> rollbackStack;

        public CancellationService(RoomInventory inventory, BookingHistory history) {
            this.inventory = inventory;
            this.history = history;
            this.rollbackStack = new Stack<>();
        }

        public void cancelBooking(String reservationId) {

            System.out.println("\nProcessing cancellation for: " + reservationId);

            // Validation
            Reservation r = history.getReservation(reservationId);

            if (r == null) {
                System.out.println("Cancellation Failed: Reservation not found.");
                return;
            }

            if (r.isCancelled()) {
                System.out.println("Cancellation Failed: Already cancelled.");
                return;
            }

            // Step 1: Record room ID in rollback stack
            rollbackStack.push(r.getRoomId());

            // Step 2: Restore inventory
            inventory.increment(r.getRoomType());

            // Step 3: Mark as cancelled
            r.cancel();

            // Confirmation
            System.out.println("Cancellation Successful!");
            System.out.println("Room Released: " + rollbackStack.peek());
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 10.0\n");

        // Setup inventory & history
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R001", "Aryan", "Single Room", "SINGLEROOM_1");
        Reservation r2 = new Reservation("R002", "Rahul", "Double Room", "DOUBLEROOM_1");

        history.addReservation(r1);
        history.addReservation(r2);

        // Cancellation service
        CancellationService service = new CancellationService(inventory, history);

        // Perform cancellations
        service.cancelBooking("R001"); // valid
        service.cancelBooking("R001"); // duplicate cancel
        service.cancelBooking("R999"); // invalid ID

        // Display final state
        history.displayAll();
        inventory.display();

        System.out.println("\nSystem state restored safely after cancellations.");
    }
}