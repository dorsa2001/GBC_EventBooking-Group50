print("START ApprovalService Initialization");

// Switch to the appropriate database
db = db.getSiblingDB("approval-service");


db.createUser({
    user: "admin",
    pwd: "password",
    roles: [{ role: "readWrite", db: "approval-service" }]
});


db.createCollection("approvals");



print("END ApprovalService Initialization");
