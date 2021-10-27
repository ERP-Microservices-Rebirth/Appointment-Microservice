package tn.esprit.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import tn.esprit.erp.entity.Appointment;
import tn.esprit.erp.service.IAppointmentService;

@RestController
@Tag(name = "Appointment REST API")
@RequestMapping(value = "/api/appointments")
public class AppointmentRestController {
	@Autowired
	IAppointmentService appointmentService;
	
	// Ajouter un Rendez-Vous : http://localhost:8081/api/appointments
	@PostMapping
	@Operation(summary = "Add an appointment", responses = {
	      @ApiResponse(description = "Appointment added successfully", responseCode = "200",
	           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Appointment> addAppointment(@Valid @RequestBody Appointment appointment) {
		return new ResponseEntity<>(appointmentService.createAppointment(appointment), HttpStatus.OK);
	}
	
	// Filtrer RDVs par Clients : http://localhost:8081/api/customers/client/{id}
	@GetMapping(value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Filter appointments by Customer", responses = {
	      @ApiResponse(description = "Appointments by Customer List", responseCode = "200",
	           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Appointment>> findCustomersBySecteur(@PathVariable(value = "id") String customerID) {
		return new ResponseEntity<>(appointmentService.retrieveAppointmentsByCustomer(customerID), HttpStatus.OK);
	}
	
	// Filtrer RDVs par Rating : http://localhost:8081/api/appointments/rating/{rating}
	@GetMapping(value = "/rating/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Filter appointments by Rating", responses = {
	      @ApiResponse(description = "Appointments by Rating List", responseCode = "200",
	           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Appointment>> findCustomersByRating(@PathVariable(value = "rating") int rating) {
		return new ResponseEntity<>(appointmentService.retrieveAppointmentsByRating(rating), HttpStatus.OK);
	}
	
	// Supprimer un rendez-vous : http://localhost:8081/api/appointments/{id}
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an appointment using his ID", responses = {
	     @ApiResponse(description = "Appointment deleted successfully", responseCode = "200",
	           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseBody
	public void removeAppointment(@PathVariable("id") String id) {
		appointmentService.deleteAppointment(id);
	}
		
	// Supprimer tous les RDVs : http://localhost:8081/api/appointments
	@DeleteMapping
	@Operation(summary = "Delete all appointments", responses = {
		@ApiResponse(description = "All Appointments has been deleted successfully", responseCode = "200",
		       content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseBody
	public void removeAllAppointments() {
		appointmentService.deleteAllAppointments();
	}
	
	// Modifier un RDV : http://localhost:8081/api/appointments/{id}
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update an appointment", responses = {
	    @ApiResponse(description = "Appointment updated successfully", responseCode = "200",
	           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Appointment> modifyAppointment(@PathVariable(value = "id") String id,
				@Valid @RequestBody Appointment appointment) {
		return new ResponseEntity<>(appointmentService.updateAppointment(id, appointment), HttpStatus.OK);
	}
	
	//Modifier le Rating d'un RDV : http://localhost:8081/api/appointments/{id}/rating
	@PatchMapping(value = "/{id}/rating", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json-patch+json")
	@Operation(summary = "Update appointments Rating value", responses = {
		      @ApiResponse(description = "Appointments Rating updated successfully", responseCode = "200",
		           content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
		})
	@ResponseStatus(HttpStatus.OK)
	public Appointment patchAppointmentRating(@RequestBody Appointment appointment, @PathVariable("id") String id) {
		Appointment appointment2 = appointmentService.retrieveAppointment(id);
		if(appointment2!=null) {
            appointment2 = appointmentService.updateRating(appointment2,appointment.getRating());
        } else {
            throw new RuntimeException();
        }
		return appointment2;
	}
	
	//Modifier les remarques d'un RDV : http://localhost:8081/api/appointments/{id}/remarque
	@PatchMapping(value = "/{id}/remarque", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update appointments feedbacks", responses = {
			 @ApiResponse(description = "Appointments Feedbacks updated successfully", responseCode = "200",
			      content = @Content(mediaType = "application/json",schema = @Schema(implementation = Appointment.class)))
	})
	@ResponseStatus(HttpStatus.OK)
	public Appointment patchAppointmentRemarques(@RequestBody Appointment appointment, @PathVariable("id") String id) {
		Appointment appointment2 = appointmentService.retrieveAppointment(id);
		if(appointment2!=null) {
	         appointment2 = appointmentService.updateFeedbacks(appointment2,appointment.getRemarque());
	    } else {
	         throw new RuntimeException();
	    }
		return appointment2;
	}
		
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
		   String fieldName = ((FieldError) error).getField();
		   String errorMessage = error.getDefaultMessage();
		   errors.put(fieldName, errorMessage);
		   });
		return errors;
	}
}
