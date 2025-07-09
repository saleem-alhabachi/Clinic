package com.example.clinic.Controller;

import com.example.clinic.Entity.Appointment;
import com.example.clinic.Entity.Doctor;
import com.example.clinic.Entity.Patient;
import com.example.clinic.Repository.AppointmentRepository;
import com.example.clinic.Repository.DoctorRepository;
import com.example.clinic.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired  AppointmentRepository appointmentRepo;
    @Autowired  DoctorRepository doctorRepo;
    @Autowired  PatientRepository patientRepo;

    String result = "";

    // ✅ Get all appointments with full info
    @GetMapping
    public String getAllAppointments() {
        result = "";
        appointmentRepo.findAll().forEach(app -> {
            result += "Appointment ID: " + app.getId() + "<br>";
            result += "Doctor: " + app.getDoctor().getName() + " (" + app.getDoctor().getSpecialization() + ")<br>";
            result += "Patient: " + app.getPatient().getName() + " - " + app.getPatient().getPhone() + "<br>";
            result += "Date: " + app.getAppointmentTime() + "<br>";
            result += "Status: " + app.getStatus() + "<br><br>";
        });
        return result;
    }

    // ✅ Get appointments for a specific patient
    @GetMapping("/patient/{id}")
    public String getAppointmentsByPatient(@PathVariable Long id) {
        result = "";
        Patient p = patientRepo.findById(id).orElseThrow();
        result += "Appointments for: " + p.getName() + "<br><br>";
        p.getAppointments().forEach(app -> {
            result += "Doctor: " + app.getDoctor().getName() + "<br>";
            result += "Time: " + app.getAppointmentTime() + "<br>";
            result += "Status: " + app.getStatus() + "<br><br>";
        });
        return result;
    }

    // ✅ Book a new appointment (via doctorId, patientId, dateTime string)
    @PostMapping("/new/{doctorId}/{patientId}/{dateTime}")
    public String bookAppointment(@PathVariable Long doctorId,
                                  @PathVariable Long patientId,
                                  @PathVariable String dateTime) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow();
        Patient patient = patientRepo.findById(patientId).orElseThrow();

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(LocalDateTime.parse(dateTime));
        appointment.setStatus(Appointment.Status.SCHEDULED);

        appointmentRepo.save(appointment);
        return "Appointment booked successfully!";
    }

    // ✅ Delete (cancel) appointment
    @DeleteMapping("/cancel/{appointmentId}")
    public String cancelAppointment(@PathVariable Long appointmentId) {
        appointmentRepo.deleteById(appointmentId);
        return "Appointment cancelled successfully.";
    }

    // ✅ Optional: Filter appointments by status
    @GetMapping("/status/{status}")
    public String getByStatus(@PathVariable Appointment.Status status) {
        result = "";
        List<Appointment> apps = appointmentRepo.findAll();
        apps.stream()
            .filter(app -> app.getStatus() == status)
            .forEach(app -> {
                result += "Doctor: " + app.getDoctor().getName() + ", ";
                result += "Patient: " + app.getPatient().getName() + ", ";
                result += "Time: " + app.getAppointmentTime() + "<br>";
            });
        return result;
    }
}





