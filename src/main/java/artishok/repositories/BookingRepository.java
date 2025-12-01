package artishok.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import artishok.entities.Booking;
import artishok.entities.enums.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByArtistId(Long artistId);

	List<Booking> findByStatus(BookingStatus status);

	List<Booking> findByExhibitionStandId(Long standId);

	// Для художника: его бронирования (прецедент 3.1.3.9)
	@Query("SELECT b FROM Booking b WHERE b.artist.id = :artistId " + "ORDER BY b.bookingDate DESC")
	List<Booking> findArtistBookings(@Param("artistId") Long artistId);
	
	 // Для владельца галереи: бронирования на его мероприятиях (прецедент 3.1.2.12)
    @Query("SELECT b FROM Booking b " +
           "WHERE b.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId " +
           "AND b.status != 'CANCELLED' " +
           "ORDER BY b.bookingDate DESC")
    List<Booking> findBookingsByGalleryId(@Param("galleryId") Long galleryId);
    
    // Для подтверждения/отклонения бронирования (прецедент 6)
    @Query("SELECT b FROM Booking b WHERE b.id = :bookingId " +
           "AND b.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId")
    Optional<Booking> findBookingForGallery(@Param("bookingId") Long bookingId, 
                                           @Param("galleryId") Long galleryId);
    
    // Проверка существования активного бронирования на стенд
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
           "WHERE b.exhibitionStand.id = :standId " +
           "AND b.status IN ('PENDING', 'CONFIRMED') " +
           "AND b.bookingDate >= :startDate")
    boolean existsActiveBookingForStand(@Param("standId") Long standId, 
                                       @Param("startDate") LocalDateTime startDate);
    
    // Статистика по выставке
    @Query("SELECT COUNT(b) FROM Booking b " +
           "WHERE b.exhibitionStand.exhibitionHallMap.exhibitionEvent.id = :eventId " +
           "AND b.status = :status")
    Long countByEventAndStatus(@Param("eventId") Long eventId, 
                              @Param("status") BookingStatus status);
}
