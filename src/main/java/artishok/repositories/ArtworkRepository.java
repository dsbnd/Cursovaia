package artishok.repositories;

import artishok.entities.Artwork;
import artishok.entities.Booking;
import artishok.entities.enums.ArtworkStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

	List<Artwork> findByBooking(Booking booking);

	List<Artwork> findByBookingId(Long bookingId);

	List<Artwork> findByStatus(ArtworkStatus status);

	@Query("SELECT a FROM Artwork a WHERE a.booking.artist.id = :artistId")
	List<Artwork> findByArtistId(@Param("artistId") Long artistId);

	@Query("SELECT a FROM Artwork a WHERE a.booking.artist.id = :artistId AND a.status = :status")
	List<Artwork> findByArtistIdAndStatus(@Param("artistId") Long artistId, @Param("status") ArtworkStatus status);

	List<Artwork> findByTitleContainingIgnoreCase(String title);

	List<Artwork> findByTechniqueContainingIgnoreCase(String technique);

	List<Artwork> findByCreationYear(Integer year);

	List<Artwork> findByCreationYearBetween(Integer startYear, Integer endYear);

	List<Artwork> findByStatusOrderByCreationYearDesc(ArtworkStatus status);

	@Query("SELECT a FROM Artwork a WHERE a.booking.exhibitionStand.exhibitionHallMap.exhibitionEvent.id = :eventId")
	List<Artwork> findByExhibitionEventId(@Param("eventId") Long eventId);

	@Query("SELECT a FROM Artwork a WHERE a.booking.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId")
	List<Artwork> findByGalleryId(@Param("galleryId") Long galleryId);

	@Query("SELECT a FROM Artwork a WHERE a.booking.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId AND a.status = :status")
	List<Artwork> findByGalleryIdAndStatus(@Param("galleryId") Long galleryId, @Param("status") ArtworkStatus status);

	@Query("SELECT COUNT(a) > 0 FROM Artwork a WHERE LOWER(a.title) = LOWER(:title) AND a.booking.artist.id = :artistId")
	boolean existsByTitleAndArtistId(@Param("title") String title, @Param("artistId") Long artistId);
}