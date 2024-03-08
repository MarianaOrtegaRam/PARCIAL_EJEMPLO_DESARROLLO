package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    /**
     * Asocia una especialidad existente a un medico
     *
     * @param medicoId       Identificador de la instancia de Medico
     * @param especialidadId Identificador de la instancia de Especialidad
     * @return Instancia de EspecialidadEntity que fue asociada a Medico
     */
    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle una especialidad al medico con id = {0}", medicoId);
        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
        if (especialidadEntity.isEmpty()) {
            throw new EntityNotFoundException("ESPECIALIDAD_NOT_FOUND");
        }
        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        if (medicoEntity.isEmpty()) {
            throw new EntityNotFoundException("BOOK_NOT_FOUND");
        }

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Termina proceso de asociarle una especialidad al medico con id = {0}", medicoId);
        return especialidadEntity.get();
    }

}
