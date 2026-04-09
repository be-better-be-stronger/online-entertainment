package com.poly.dto.response;

import java.util.List;

import com.poly.dto.VideoDTO;

public class FavoritesResponse {
	
	private List<VideoDTO> items;
    private int page;
    private int totalPages;
    
	public FavoritesResponse(List<VideoDTO> items, int page, int totalPages) {
		super();
		this.items = items;
		this.page = page;
		this.totalPages = totalPages;
	}

	public List<VideoDTO> getItems() {
		return items;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}	
    
}
