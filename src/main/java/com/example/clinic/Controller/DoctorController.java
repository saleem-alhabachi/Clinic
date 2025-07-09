package com.example.clinic.Controller;

import com.example.clinic.Entity.Doctor;
import com.example.clinic.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepo;

    @PostMapping("save")
    public String addDoctor(@RequestBody Doctor doctor) {
        doctorRepo.save(doctor);
        return "Doctor saved";
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    @GetMapping("{id}")
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepo.findById(id);
    }
}
