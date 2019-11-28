package br.com.xlo.repository;

import br.com.xlo.model.Usuario;
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
            + "WHERE mv.marca = ?2 AND mv.tipoVeiculo.tipoNome = ?1 "
            + "ORDER BY v.descricao ")
    Page<Veiculo> findByMarcaPage(String tipoVeiculo, String marcaVeiculo, Pageable pageable);

    @Query("SELECT v FROM Veiculo v "
            + "JOIN MarcaVeiculo mv ON v.marca.id = mv.id "
            + "WHERE mv.marca = ?2 AND mv.tipoVeiculo.tipoNome = ?1 "
            + "AND LOWER(v.descricao) LIKE LOWER(CONCAT('%',?3,'%')) ")
    Page<Veiculo> findByMarcaAndDescricaoPage(String tipoVeiculo, String marcaVeiculo, String descricaoVeiculo, Pageable pageable);

    Page<Veiculo> findAll(Pageable p);
    
    @Query("SELECT v FROM Veiculo v " 
            + "JOIN Usuario u ON v.idUsuario.id = u.id " 
            + "WHERE u.username = ?1")
    List<Veiculo> findVehiclesByUserName(String username);
    
    Veiculo save(Veiculo veiculo);
    
    Veiculo findById(Integer id);
    
    @Query(value = "SELECT DISTINCT v.descricao, v.marca, v.ano, v.kmRodado, v.preco, (select string_agg(cast(op.nome as text)) from Opcionais, OpcionaisVeiculo" +
            "WHERE Opcionais.id = OpcionaisVeiculo.idOpcionais) AS opcionais" +
            "FROM Veiculo v, Opcionais op, OpcionaisVeiculo opv" +
            "WHERE v.id = opv.idVeiculo AND op.id = opv.idOpcionais", nativeQuery = true)
    List<Veiculo> listaVeiculoEOpcionais();
    
    /*SELECT DISTINCT v.descricao, v.marca, v.ano, v.km_rodado, v.preco, (select string_agg(op.nome::text, ',') from opcionais op, opcionais_veiculo opv
        where op.id = opv.id_opcionais) AS opcionais
        FROM veiculo v, opcionais op, opcionais_veiculo opv
        WHERE v.id = opv.id_veiculo AND op.id = opv.id_opcionais;*/
    
    @Override
    void delete(Veiculo v);
}
