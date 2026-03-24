

public class UC2 {

    // ================= ABSTRACT CLASS =================
    static abstract class Room {

        // Encapsulation
        private String roomType;
        private int beds;
        private double price;

        // Constructor
        public Room(String roomType, int beds, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.price = price;
        }

        // Getters
        public String getRoomType() {
            return roomType;
        }

        public int getBeds() {
            return beds;
        }

        public double getPrice() {
            return price;
        }

        // Abstract Method
        public abstract void displayRoomDetails();
    }

    // ================= SINGLE ROOM =================
    static class SingleRoom extends Room {

        public SingleRoom() {
            super("Single Room", 1, 1000.0);
        }

        @Override
        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    // ================= DOUBLE ROOM =================
    static class DoubleRoom extends Room {

        public DoubleRoom() {
            super("Double Room", 2, 2000.0);
        }

        @Override
        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    // ================= SUITE ROOM =================
    static class SuiteRoom extends Room {

        public SuiteRoom() {
            super("Suite Room", 3, 5000.0);
        }

        @Override
        public void displayRoomDetails() {
            System.out.println("Room Type: " + getRoomType());
            System.out.println("Beds: " + getBeds());
            System.out.println("Price: ₹" + getPrice());
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 2.1\n");

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability (No Data Structures)
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Output
        System.out.println("---- Room Details ----\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("System initialized successfully.");
    }
}