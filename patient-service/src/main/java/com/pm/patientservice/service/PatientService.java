package com.pm.patientservice.service;


import billing.BillingResponse;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientDoesNotExistException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer
    ) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        log.info("getiing patients");
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO getPatientById(UUID id) {

        Patient patient=patientRepository.findById(id).orElseThrow(()-> new PatientDoesNotExistException("The patient with ID is not present"));

        return PatientMapper.toDTO(patient);
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        log.info("Calling gRPC BillingService with");


        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {

            throw new EmailAlreadyExistsException("A Patient with this email" + "already exists" + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        try {
            log.info("Calling gRPC BillingService with patientId={}, name={}, email={}",
                    newPatient.getId(), newPatient.getName(), newPatient.getEmail());

            BillingResponse response= billingServiceGrpcClient.createBillingAccount(
                    newPatient.getId().toString(),
                    newPatient.getName(),
                    newPatient.getEmail()
            );
            System.out.println(response);

            log.info("Billing account created: {}");

        } catch (Exception e) {
            log.error("gRPC call to BillingService failed", e);
        }


//        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName().toString(),newPatient.getEmail().toString() );

        kafkaProducer.sendEvent(newPatient);

        return (PatientMapper.toDTO(newPatient));

    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        System.out.println("req recevied");

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID:" + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)) {
            throw new EmailAlreadyExistsException("A Patient with this email already exits"+ patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        return PatientMapper.toDTO(patientRepository.save(patient));





    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
