package uz.telegraph.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.telegraph.entity.TelegraphEntity;

import java.util.UUID;

@Repository
public interface TelegraphRepository extends JpaRepository<TelegraphEntity, UUID> {
    TelegraphEntity findTelegraphEntitiesByUser_Id(UUID userId);
}
