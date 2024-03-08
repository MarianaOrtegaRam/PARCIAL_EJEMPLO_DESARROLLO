package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    /**
     * Se encarga de crear un medico en la base de datos.
     *
     * @param medico Objeto de MedicoEntity con los datos nuevos
     * @return Objeto de MedicoEntity con los datos nuevos y su ID.
     * @throws IllegalOperationException si
     */

    @Transactional
    public MedicoEntity createMedicos(MedicoEntity medico) throws IllegalOperationException {
        log.info("Inicia proceso de creación del medico");
        String registro = (medico.getRegistroMedico()).substring(0, 2);
        if (!registro.equals("RM")) {
            throw new IllegalOperationException("Registro medico invalido");
        }
        log.info("Termina proceso de creación del medico");
        return medicoRepository.save(medico);
    }

}
