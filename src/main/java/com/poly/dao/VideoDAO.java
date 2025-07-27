package com.poly.dao;

import java.util.List;

import com.poly.entity.Video;

public interface VideoDAO {
	List<Video> findTop6ByViews();		// Top 6 video nhiều views nhất
	List<Video> findTop6ByLatest();		// Top 6 videos mới nhất
           
    List<Video> findAll(int page, int size);   // Phân trang
    long count();                              // Tổng số video
    Video findById(String id);
    void create(Video video);
    void update(Video video);
    void delete(String id);
}
