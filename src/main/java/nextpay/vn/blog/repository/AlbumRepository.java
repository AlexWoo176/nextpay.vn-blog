package nextpay.vn.blog.repository;

import nextpay.vn.blog.entity.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByCreatedBy(Long userId, Pageable pageable);
}
