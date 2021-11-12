package node.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import node.entity.PairEntity;

@Repository
public interface PairRepository extends JpaRepository<PairEntity,Long> { 
}