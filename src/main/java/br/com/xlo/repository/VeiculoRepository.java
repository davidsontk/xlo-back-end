package br.com.xlo.repository;

import br.com.xlo.model.Veiculo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Davidson
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v "
            + "JOIN MarcaVeiculo mv ON v.marca.id = mv.id "
            + "WHERE mv.marca = ?1 "
            + "ORDER BY v.descricao ")
    List<Veiculo> findByMarca(String marcaVeiculo);

    @Query("SELECT v FROM Veiculo v "
            + "JOIN MarcaVeiculo mv ON v.marca.id = mv.id "
            + "WHERE mv.marca = ?1 "
            + "ORDER BY v.descricao ")
    Page<Veiculo> findByMarcaPage(String marcaVeiculo, Pageable pageable);

    @Query("SELECT v FROM Veiculo v "
            + "JOIN MarcaVeiculo mv ON v.marca.id = mv.id "
            + "WHERE mv.marca = ?1 "
            + "AND LOWER(v.descricao) LIKE LOWER(CONCAT('%',?2,'%')) ")
    Page<Veiculo> findByMarcaAndDescricaoPage(String marcaVeiculo, String descricaoVeiculo, Pageable pageable);

}
