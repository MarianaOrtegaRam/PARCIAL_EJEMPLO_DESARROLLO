package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository;

    /**
     * Se encarga de crear una especialidad en la base de datos.
     *
     * @param especialidad Objeto de EspecialidadEntity con los datos nuevos
     * @return Objeto de EspecialidadEntity con los datos nuevos y su ID.
     * @throws IllegalOperationException
     */

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException {
        log.info("Inicia proceso de creación de la especialidad");
        String descripcionEsp = especialidad.getDescripcion();
        int largoDescripcion = descripcionEsp.length();
        if (largoDescripcion < 10) {
            throw new IllegalOperationException("Descripcion invalida");
        }
        log.info("Termina proceso de creación de la especialidad");
        return especialidadRepository.save(especialidad);
    }
}