package tn.esprit.erp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import tn.esprit.erp.entity.Appointment;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
	List<Appointment> findByCustomerID(String customerID);
	List<Appointment> findByRating(Integer rating);
}
