
import java.util.LinkedList;
import java.util.Queue;

public class UC5 {

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

        public void displayReservation() {
            System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
        }
    }

    // ================= BOOKING QUEUE =================
    static class BookingQueue {

        private Queue<Reservation> queue;

        public BookingQueue() {
            queue = new LinkedList<>();
        }

        // Add request (FIFO insertion)
        public void addRequest(Reservation reservation) {
            queue.add(reservation);
            System.out.println("Request added for: " + reservation.getGuestName());
        }

        // Display queue (arrival order preserved)
        public void displayQueue() {
            System.out.println("\n---- Booking Request Queue ----");

            for (Reservation r : queue) {
                r.displayReservation();
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 5.0\n");

        // Initialize Booking Queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate Guest Requests (Arrival Order)
        Reservation r1 = new Reservation("Aryan", "Single Room");
        Reservation r2 = new Reservation("Rahul", "Double Room");
        Reservation r3 = new Reservation("Priya", "Suite Room");
        Reservation r4 = new Reservation("Neha", "Single Room");

        // Add to Queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Display Queue
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in FIFO order.");
        System.out.println("No inventory updates performed at this stage.");
    }
}