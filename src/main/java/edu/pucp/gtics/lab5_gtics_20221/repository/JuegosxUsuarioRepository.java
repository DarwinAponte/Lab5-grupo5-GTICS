package edu.pucp.gtics.lab5_gtics_20221.repository;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juegos;
import edu.pucp.gtics.lab5_gtics_20221.entity.JuegosUserDto;
import edu.pucp.gtics.lab5_gtics_20221.entity.JuegosxUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JuegosxUsuarioRepository extends JpaRepository<JuegosxUsuario, Integer> {    @Transactional
@Modifying
@Query(value = "Insert INTO juegosxusuario (idusuario, idjuego, cantidad) VALUES (?,?,1)", nativeQuery = true)
void registrarJuegoPorUser(int idusuario, int idjuego);
}
