
import java.util.*;

public class UC7 {

    // ================= RESERVATION =================
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
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
    }

    // ================= ADD-ON SERVICE =================
    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    // ================= SERVICE MANAGER =================
    static class AddOnServiceManager {

        // Map: ReservationID → List of Services
        private HashMap<String, List<AddOnService>> serviceMap;

        public AddOnServiceManager() {
            serviceMap = new HashMap<>();
        }

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {

            serviceMap.putIfAbsent(reservationId, new ArrayList<>());
            serviceMap.get(reservationId).add(service);

            System.out.println("Service added: " + service.getServiceName() +
                    " for Reservation: " + reservationId);
        }

        // Calculate total cost
        public double calculateTotalCost(String reservationId) {

            double total = 0;

            List<AddOnService> services = serviceMap.get(reservationId);

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.getCost();
                }
            }

            return total;
        }

        // Display services
        public void displayServices(String reservationId) {

            System.out.println("\n---- Services for Reservation: " + reservationId + " ----");

            List<AddOnService> services = serviceMap.get(reservationId);

            if (services == null || services.isEmpty()) {
                System.out.println("No services selected.");
                return;
            }

            for (AddOnService s : services) {
                System.out.println(s.getServiceName() + " : ₹" + s.getCost());
            }

            System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 7.0\n");

        // Existing reservation (from UC6 concept)
        Reservation r1 = new Reservation("R001", "Aryan", "Single Room");

        // Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services (One-to-Many)
        manager.addService(r1.getReservationId(), new AddOnService("Breakfast", 300));
        manager.addService(r1.getReservationId(), new AddOnService("Airport Pickup", 800));
        manager.addService(r1.getReservationId(), new AddOnService("Extra Bed", 500));

        // Display services + cost
        manager.displayServices(r1.getReservationId());

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}