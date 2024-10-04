package pl.kurs.finaltesttest.service;

import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctor(DoctorDTO doctorDto);

    DoctorDTO getDoctor(Long id);

    void deleteDoctor(Long id);

    void updateDoctor(Long id, DoctorDTO doctorDto);

    List<PatientDTO> getPatientsWithAppointments(Long doctorId);

    List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId);

    void cancelAppointmentByDoctor(Long appointmentId, Long doctorId);
}