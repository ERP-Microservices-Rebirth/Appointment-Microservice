package tn.esprit.erp.entity;

import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "appointment")
public class Appointment {
	@Id
    private String id;

	@Field(value = "scheduled_at")
	@FutureOrPresent
	@NotNull(message = "You have to schedule your Appointment")
    private Date scheduledAt;

	@Field(value = "created_at")
    private Date createdAt;
	
	@Min(value = 0, message = "Rating value must be higher than 0")
	@Max(value = 5, message = "No more than 5 note")
	private int rating;
	
	private String remarque;
	
	@Field(value = "customer_id")
	@NotNull(message = "You must choose a customer")
	private String customerID;
	
	@Field(value = "products_id")
	@NotNull(message = "You must choose some products")
	private String[] productsID;

	public Appointment(Date scheduledAt, int rating, String remarque) {
		super();
		this.scheduledAt = scheduledAt;
		this.createdAt = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
		this.rating = rating;
		this.remarque = remarque;
	}

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String[] getProductsID() {
		return productsID;
	}

	public void setProductsID(String[] productsID) {
		this.productsID = productsID;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
