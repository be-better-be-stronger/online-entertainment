package com.poly.dto.response;

import java.util.List;

import com.poly.dto.VideoDTO;

public class HomeResponse {
	List<VideoDTO> popularVideos;
    List<VideoDTO> newVideos;
    List<VideoDTO> pagedVideos;
    int currentPage;
    int totalPages;
    int startPage;
    int endPage;
    
	public List<VideoDTO> getPopularVideos() {
		return popularVideos;
	}
	public void setPopularVideos(List<VideoDTO> popularVideos) {
		this.popularVideos = popularVideos;
	}
	public List<VideoDTO> getNewVideos() {
		return newVideos;
	}
	public void setNewVideos(List<VideoDTO> newVideos) {
		this.newVideos = newVideos;
	}
	public List<VideoDTO> getPagedVideos() {
		return pagedVideos;
	}
	public void setPagedVideos(List<VideoDTO> pagedVideos) {
		this.pagedVideos = pagedVideos;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
}
