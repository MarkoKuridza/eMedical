package org.emedical.config;

import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.entities.AppointmentEntity;
import org.emedical.models.entities.MedicalRecordEntity;
import org.emedical.models.entities.WaitingRoomEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.typeMap(MedicalRecordEntity.class, MedicalRecord.class).addMappings(m -> {
            m.map(src -> src.getDoctor().getFirstName(), MedicalRecord::setDoctorFirstName);
            m.map(src -> src.getDoctor().getLastName(), MedicalRecord::setDoctorLastName);
        });

        modelMapper.typeMap(AppointmentEntity.class, Appointment.class).addMappings(a -> {
            a.map(src -> src.getDoctor().getFirstName(), Appointment::setDoctorFirstName);
            a.map(src -> src.getDoctor().getLastName(), Appointment::setDoctorLastName);
            a.map(src -> src.getNurse().getFirstName(), Appointment::setNurseFirstName);
            a.map(src -> src.getNurse().getLastName(), Appointment::setNurseLastName);
            a.map(src -> src.getPatient().getFirstName(), Appointment::setPatientFirstName);
            a.map(src -> src.getPatient().getLastName(), Appointment::setPatientLastName);
        });

        modelMapper.typeMap(WaitingRoomEntity.class, WaitingRoom.class).addMappings(w -> {
            w.map(src -> src.getDoctor().getFirstName(), WaitingRoom::setDoctorFirstName);
            w.map(src -> src.getDoctor().getLastName(), WaitingRoom::setDoctorLastName);
            w.map(src -> src.getNurse().getFirstName(), WaitingRoom::setNurseFirstName);
            w.map(src -> src.getNurse().getLastName(), WaitingRoom::setNurseLastName);
            w.map(src -> src.getPatient().getFirstName(), WaitingRoom::setPatientFirstName);
            w.map(src -> src.getPatient().getLastName(), WaitingRoom::setPatientLastName);
            w.map(src -> src.getAppointment().getAppointmentStatus(), WaitingRoom::setAppointmentStatus);
        });

        return modelMapper;
    }
}
