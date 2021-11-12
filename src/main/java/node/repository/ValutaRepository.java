package node.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import node.entity.ValutaEntity;

@Repository
public interface ValutaRepository extends JpaRepository<ValutaEntity,Long>{

	List<ValutaEntity> findByPair2(String pair2);

}
