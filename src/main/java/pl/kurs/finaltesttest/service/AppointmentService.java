package pl.kurs.finaltesttest.service;

import pl.kurs.finaltesttest.dto.AppointmentDTO;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDto, Long patientId, Long doctorId);

    void cancelAppointmentByPatient(Long appointmentId, Long patientId);

    void cancelAppointmentByDoctor(Long appointmentId, Long doctorId);
}
