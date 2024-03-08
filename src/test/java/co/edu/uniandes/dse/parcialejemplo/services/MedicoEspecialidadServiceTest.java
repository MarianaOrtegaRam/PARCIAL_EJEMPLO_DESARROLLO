package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;

import co.edu.uniandes.dse.parcialejemplo.services.MedicoService;
import co.edu.uniandes.dse.parcialejemplo.services.EspecialidadService;
import co.edu.uniandes.dse.parcialejemplo.services.MedicoEspecialidadService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ MedicoEspecialidadService.class })

public class MedicoEspecialidadServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    private PodamFactory factory = new PodamFactoryImpl();

    private MedicoEntity medico = new MedicoEntity();
    private EspecialidadEntity especialidad = new EspecialidadEntity();
    private List<EspecialidadEntity> especialidadesList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialistaEntiry").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {

        medico = factory.manufacturePojo(MedicoEntity.class);

        entityManager.persist(medico);

        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(entity);
            entity.getEspecialistas().add(medico);
            especialidadesList.add(entity);
            medico.getEspecialidades().add(entity);
        }
    }

    /**
     * Prueba para asociar una especialidad a un medico.
     *
     */
    @Test
    void testAddAuthor() throws EntityNotFoundException, IllegalOperationException {
        MedicoEntity newMedico = factory.manufacturePojo(MedicoEntity.class);
        newMedico.setRegistroMedico("RM13586");
        entityManager.persist(newMedico);

        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);

        medicoEspecialidadService.addEspecialidad(newMedico.getId(), especialidad.getId());
        /*
         * EspecialidadEntity lastEspecialidad =
         * medicoEspecialidadService.getAuthor(newBook.getId(), author.getId());
         * assertEquals(author.getId(), lastAuthor.getId());
         * assertEquals(author.getBirthDate(), lastAuthor.getBirthDate());
         * assertEquals(author.getDescription(), lastAuthor.getDescription());
         * assertEquals(author.getImage(), lastAuthor.getImage());
         * assertEquals(author.getName(), lastAuthor.getName());
         */
    }

}
