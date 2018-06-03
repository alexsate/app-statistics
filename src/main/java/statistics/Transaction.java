package statistics;

import java.time.Instant;

public class Transaction {

    private final double amount;
    private final Instant timestamp;
    
	public Transaction(double amount, Instant timestamp) {
		super();
		this.amount = amount;
		this.timestamp = timestamp;
	}
	
	public double getAmount() {
		return amount;
	}
	public Instant getTimestamp() {
		return timestamp;
	}

    /*public Transaction() {
    	Instant now = Instant.now();
    }*/

    
    
}
