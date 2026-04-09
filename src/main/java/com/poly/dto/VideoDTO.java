package com.poly.dto;

import java.util.Date;

public class VideoDTO {
	private String id;
    private String title;
    private String description;
    private String link;
    private byte[] poster;
    private int views;
    private Date createdDate;

    private boolean liked;
    private long likeCount;
    private long shareCount;
    
	public VideoDTO(String id, String title, String description, String link, byte[] poster, int views,
			Date createdDate, boolean liked, long likeCount, long shareCount) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.poster = poster;
		this.views = views;
		this.createdDate = createdDate;
		this.liked = liked;
		this.likeCount = likeCount;
		this.shareCount = shareCount;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}

	public byte[] getPoster() {
		return poster;
	}

	public int getViews() {
		return views;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public boolean isLiked() {
		return liked;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public long getShareCount() {
		return shareCount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPoster(byte[] poster) {
		this.poster = poster;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public void setShareCount(long shareCount) {
		this.shareCount = shareCount;
	}
	
	
	
    
	
}
