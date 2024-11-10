print("START BookingService Initialization");

// Switch to the appropriate database
db = db.getSiblingDB("booking-service");


db.createUser({
    user: "admin",
    pwd: "password",
    roles: [{ role: "readWrite", db: "booking-service" }]
});


db.createCollection("bookings");


print("END BookingService Initialization");
