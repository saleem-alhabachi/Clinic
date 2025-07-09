package com.example.clinic.Controller;

import com.example.clinic.Entity.Patient;
import com.example.clinic.Repository.PatientRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    PatientRepository patientRepo;

    @PostMapping("save")
    public String addPatient(@RequestBody Patient patient) {
        patientRepo.save(patient);
        return "Patient saved";
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    @GetMapping("{id}")
    public Optional<Patient> getPatientById(@PathVariable Long id) {
       return patientRepo.findById(id);
        
    }
}
