package tn.esprit.erp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tn.esprit.erp.entity.Appointment;

@Service
public interface IAppointmentService {
	//CRUD
	Appointment createAppointment(Appointment appointment);
	Appointment retrieveAppointment(String id);
	List<Appointment> retrieveAppointmentsByCustomer(String customerID);
	List<Appointment> retrieveAppointmentsByRating(int rating);
	Appointment updateAppointment(String id, Appointment appointment);
	void deleteAppointment(String id);
	void deleteAllAppointments();
	//Updating Fields (PATCH)
	Appointment updateRating(Appointment appointment, Integer rating);
	Appointment updateFeedbacks(Appointment appointment, String remarque);
}
