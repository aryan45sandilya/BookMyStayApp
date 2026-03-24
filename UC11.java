
import java.util.*;

public class UC11{

    // ================= RESERVATION =================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
    }

    // ================= INVENTORY =================
    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
        }

        // Synchronized → Critical Section
        public synchronized boolean allocateRoom(String roomType) {

            int available = inventory.getOrDefault(roomType, 0);

            if (available > 0) {
                inventory.put(roomType, available - 1);
                return true;
            }
            return false;
        }

        public void display() {
            System.out.println("\nFinal Inventory:");
            for (String type : inventory.keySet()) {
                System.out.println(type + " : " + inventory.get(type));
            }
        }
    }

    // ================= BOOKING PROCESSOR =================
    static class BookingProcessor {

        private Queue<Reservation> queue;
        private RoomInventory inventory;

        public BookingProcessor(RoomInventory inventory) {
            this.queue = new LinkedList<>();
            this.inventory = inventory;
        }

        // Synchronized queue access
        public synchronized void addRequest(Reservation r) {
            queue.add(r);
        }

        public synchronized Reservation getRequest() {
            return queue.poll();
        }

        // Thread task
        public void processBooking() {

            while (true) {

                Reservation r;

                // Critical section: fetch request
                synchronized (this) {
                    r = getRequest();
                }

                if (r == null) break;

                // Critical section: allocation
                boolean success = inventory.allocateRoom(r.getRoomType());

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " → Booking Confirmed for " + r.getGuestName());
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " → Booking Failed for " + r.getGuestName());
                }
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 11.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingProcessor processor = new BookingProcessor(inventory);

        // Simulate multiple guest requests
        processor.addRequest(new Reservation("Aryan", "Single Room"));
        processor.addRequest(new Reservation("Rahul", "Single Room"));
        processor.addRequest(new Reservation("Priya", "Single Room")); // extra
        processor.addRequest(new Reservation("Neha", "Single Room"));  // extra

        // Create threads (simulate concurrent users)
        Thread t1 = new Thread(() -> processor.processBooking(), "Thread-1");
        Thread t2 = new Thread(() -> processor.processBooking(), "Thread-2");
        Thread t3 = new Thread(() -> processor.processBooking(), "Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.display();

        System.out.println("\nAll bookings processed safely (No double booking).");
    }
}