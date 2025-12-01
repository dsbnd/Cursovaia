package artishok.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exhibition_hall_map")
@Getter
@Setter
public class ExhibitionHallMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_event_id", nullable = false)
    private ExhibitionEvent exhibitionEvent;

    @Column(name = "map_image_url")
    private String mapImageUrl;

    @Column(nullable = false)
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExhibitionEvent getExhibitionEvent() {
		return exhibitionEvent;
	}

	public void setExhibitionEvent(ExhibitionEvent exhibitionEvent) {
		this.exhibitionEvent = exhibitionEvent;
	}

	public String getMapImageUrl() {
		return mapImageUrl;
	}

	public void setMapImageUrl(String mapImageUrl) {
		this.mapImageUrl = mapImageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}