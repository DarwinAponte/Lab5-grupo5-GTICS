package edu.pucp.gtics.lab5_gtics_20221.repository;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegosxUsuarioRepository extends JpaRepository <Juegos , Integer> {
}
