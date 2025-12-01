package artishok.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import artishok.entities.Artwork;
import artishok.entities.enums.ArtworkStatus;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
	List<Artwork> findByBookingId(Long bookingId);

	List<Artwork> findByStatus(ArtworkStatus status);

	// Для художника: его произведения (прецедент 3.1.3.9)
	@Query("SELECT a FROM Artwork a WHERE a.booking.artist.id = :artistId")
	List<Artwork> findByArtistId(@Param("artistId") Long artistId);

	// Для выставки: все произведения на выставке
	@Query("SELECT a FROM Artwork a "
			+ "WHERE a.booking.exhibitionStand.exhibitionHallMap.exhibitionEvent.id = :eventId "
			+ "AND a.status = 'PUBLISHED'")
	List<Artwork> findByExhibitionEventId(@Param("eventId") Long eventId);

	// Для модерации контента администратором (прецедент 3.1.1.7)
	@Query("SELECT a FROM Artwork a WHERE a.status = 'DRAFT' ")
	List<Artwork> findUnpublishedArtworks();

	// Поиск по названию или описанию
	@Query("SELECT a FROM Artwork a WHERE "
			+ "(:search IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :search, '%'))) " + "AND a.status = 'PUBLISHED'")
	List<Artwork> searchPublishedArtworks(@Param("search") String search);

	// Проверка, что у художника есть подтвержденное бронирование для произведения
	@Query("SELECT COUNT(b) > 0 FROM Booking b " + "WHERE b.artist.id = :artistId " + "AND b.id = :bookingId "
			+ "AND b.status = 'CONFIRMED'")
	boolean hasConfirmedBooking(@Param("artistId") Long artistId, @Param("bookingId") Long bookingId);
}
