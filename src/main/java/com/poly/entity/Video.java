package com.poly.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;
    
  
    private String link;
    
    @Lob
    @Column(name = "poster", columnDefinition = "LONGBLOB")
    private byte[] poster;


    private Integer views = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean active = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;
    
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Share> shares;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
