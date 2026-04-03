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
    private int likeCount;
    private int shareCount;
	public VideoDTO(String id, String title, String description, String link, byte[] poster, int views,
			Date createdDate, boolean liked, int likeCount, int shareCount) {
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
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public byte[] getPoster() {
		return poster;
	}
	public void setPoster(byte[] poster) {
		this.poster = poster;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public boolean isLiked() {
		return liked;
	}
	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
    
	
}
