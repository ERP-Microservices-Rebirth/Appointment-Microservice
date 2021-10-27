package tn.esprit.erp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.erp.entity.Appointment;
import tn.esprit.erp.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public Appointment createAppointment(Appointment appointment) {
		// Ajouter un RDV
		appointment.setCreatedAt(new Date());
        return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment retrieveAppointment(String id) {
		// Affichez un RDV par son ID
		return appointmentRepository.findById(id).get();
	}

	@Override
	public Appointment updateAppointment(String id, Appointment appointment) {
		// Modifier un RDV existant
		if (appointmentRepository.findById(id).isPresent()) {
			Appointment appointmentExistant = appointmentRepository.findById(id).get();
			appointmentExistant.setScheduledAt(appointment.getScheduledAt());
			appointmentExistant.setCustomerID(appointment.getCustomerID());
			appointmentExistant.setProductsID(appointment.getProductsID());
			appointmentExistant.setRating(appointment.getRating());
			appointmentExistant.setRemarque(appointment.getRemarque());
			return appointmentRepository.save(appointmentExistant);
		} else return null;
	}

	@Override
	public void deleteAppointment(String id) {
		//Supprimer un RDV
		appointmentRepository.deleteById(id);
	}

	@Override
	public void deleteAllAppointments() {
		// Supprimez tous les RDVs
		appointmentRepository.deleteAll();
		
	}

	@Override
	public Appointment updateRating(Appointment appointment, Integer rating) {
		//Modifier la note du Potentiel Client
		appointment.setRating(rating);
        return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment updateFeedbacks(Appointment appointment, String remarque) {
		//Modifier les remarques à propos RDV
		appointment.setRemarque(remarque);
		return appointmentRepository.save(appointment);
	}

	@Override
	public List<Appointment> retrieveAppointmentsByCustomer(String customerID) {
		// Chercher les RDVs par client
		return appointmentRepository.findByCustomerID(customerID);
	}

	@Override
	public List<Appointment> retrieveAppointmentsByRating(int rating) {
		// Chercher les RDVs par note du potentiel client
		return appointmentRepository.findByRating(rating);
	}
}
