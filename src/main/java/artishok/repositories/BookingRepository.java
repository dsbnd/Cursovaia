package artishok.repositories;

import artishok.entities.Booking;
import artishok.entities.User;
import artishok.entities.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByArtist(User artist);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByExhibitionStandId(Long standId);
    
    List<Booking> findByExhibitionStandIdAndStatus(Long standId, BookingStatus status);

    List<Booking> findByArtistAndStatus(User artist, BookingStatus status);

    List<Booking> findByBookingDateAfter(LocalDateTime date);
    
    List<Booking> findByBookingDateBefore(LocalDateTime date);
    
    List<Booking> findByStatusOrderByBookingDateDesc(BookingStatus status);
    

    @Query("SELECT b FROM Booking b WHERE b.exhibitionStand.exhibitionHallMap.exhibitionEvent.id = :eventId")
    List<Booking> findByExhibitionEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT b FROM Booking b WHERE b.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId")
    List<Booking> findByGalleryId(@Param("galleryId") Long galleryId);
    
    @Query("SELECT b FROM Booking b WHERE b.exhibitionStand.exhibitionHallMap.exhibitionEvent.gallery.id = :galleryId AND b.status = :status")
    List<Booking> findByGalleryIdAndStatus(@Param("galleryId") Long galleryId, @Param("status") BookingStatus status);
    
    boolean existsByExhibitionStandIdAndStatus(Long standId, BookingStatus status);
    
    long countByStatus(BookingStatus status);
    
    long countByArtist(User artist);
    
    List<Booking> findTop10ByOrderByBookingDateDesc();
    
    List<Booking> findByBookingDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}