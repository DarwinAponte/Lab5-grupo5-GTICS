package edu.pucp.gtics.lab5_gtics_20221.repository;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juegos;
import edu.pucp.gtics.lab5_gtics_20221.entity.JuegosUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface JuegosRepository extends JpaRepository<Juegos,Integer> {
    @Query(value = "Select  j.idjuego, j.nombre, j.descripcion, g.nombre as genero, j.image as imageURL from gameshop3.juegos j " +
            "inner join gameshop4.juegosxusuario ju  on j.idjuego=ju.idjuego " +
            "inner join gameshop4.usuarios u on ju.idusuario=u.idusuario " +
            "inner join gameshop4.generos g on g.idgenero=j.idgenero Where u.idusuario= ?",nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

    @Query(value = "SELECT * FROM juegos\n" +
            "where idjuego not in\n" +
            "(select idjuego from juegosxusuario where idusuario = ?1) order by nombre DESC ",nativeQuery = true)
    List<Juegos> obtenerJuegosNoCompradosPorUser(int idusuario);
}
