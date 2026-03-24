/**
 * Class: UseCase8BookingHistoryReport
 *
 * Description:
 * Stores confirmed bookings in history and generates reports.
 * Demonstrates ordered storage and reporting without modifying data.
 *
 * Concepts:
 * - List (Ordered Storage)
 * - Historical Tracking
 * - Reporting Service
 * - Separation of Concerns
 *
 * @author Aryan
 * @version 8.0
 */

import java.util.*;

public class UC8 {

    // ================= RESERVATION =================
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;
        private String roomId;

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getRoomId() {
            return roomId;
        }

        public void display() {
            System.out.println("Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room: " + roomType +
                    " | Room ID: " + roomId);
        }
    }

    // ================= BOOKING HISTORY =================
    static class BookingHistory {

        private List<Reservation> history;

        public BookingHistory() {
            history = new ArrayList<>();
        }

        // Store confirmed booking
        public void addReservation(Reservation r) {
            history.add(r);
        }

        // Retrieve all bookings
        public List<Reservation> getAllReservations() {
            return history;
        }
    }

    // ================= REPORT SERVICE =================
    static class BookingReportService {

        // Display all bookings (read-only)
        public void displayAllBookings(List<Reservation> history) {

            System.out.println("\n---- Booking History ----");

            for (Reservation r : history) {
                r.display();
            }
        }

        // Generate summary report
        public void generateSummary(List<Reservation> history) {

            System.out.println("\n---- Booking Summary ----");

            HashMap<String, Integer> countMap = new HashMap<>();

            for (Reservation r : history) {
                String type = r.getRoomType();
                countMap.put(type, countMap.getOrDefault(type, 0) + 1);
            }

            for (String type : countMap.keySet()) {
                System.out.println(type + " Bookings: " + countMap.get(type));
            }

            System.out.println("Total Bookings: " + history.size());
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 8.0\n");

        // Booking History
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("R001", "Aryan", "Single Room", "SINGLEROOM_1"));
        history.addReservation(new Reservation("R002", "Rahul", "Double Room", "DOUBLEROOM_1"));
        history.addReservation(new Reservation("R003", "Priya", "Suite Room", "SUITEROOM_1"));
        history.addReservation(new Reservation("R004", "Neha", "Single Room", "SINGLEROOM_2"));

        // Report Service
        BookingReportService reportService = new BookingReportService();

        // Display history
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting completed (No data modified).");
    }
}