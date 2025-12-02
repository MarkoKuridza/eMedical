package org.emedical.config;

import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.entities.MedicalRecordEntity;
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
           m.map(src -> src.getDoctor().getFirst_name(), MedicalRecord::setDoctorFirstName);
           m.map(src -> src.getDoctor().getLast_name(), MedicalRecord::setDoctorLastName);
        });

        return modelMapper;
    }
}
