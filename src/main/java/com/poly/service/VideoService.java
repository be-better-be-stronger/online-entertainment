package com.poly.service;

import java.util.List;

import com.poly.dto.response.HomeResponse;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.exception.AppException;


/**
 * Service interface xử lý các nghiệp vụ liên quan đến video.
 * Là tầng trung gian giữa Controller và DAO.
 */
public interface VideoService {	
	
	/**
     * Lấy danh sách 6 video có lượt xem cao nhất.
     *
     * @return danh sách 6 video phổ biến nhất theo số lượt xem, sắp xếp giảm dần
     */
    List<Video> getTop6PopularVideos();
    
    /**
     * Lấy danh sách 6 video mới nhất được đăng tải.
     *
     * @return danh sách 6 video mới nhất, sắp xếp theo ngày tạo giảm dần
     */
    List<Video> getTop6LatestVideos();
    
    /**
     * Đếm tổng số video hiện có trong hệ thống.
     *
     * @return tổng số video
     */
	long getTotalVideos();
	
	 /**
     * Lấy danh sách video theo phân trang.
     *
     * @param page số trang (bắt đầu từ 0)
     * @param size số lượng video mỗi trang
     * @return danh sách video trong trang được chỉ định
     */
	List<Video> findAllActiveVideosByPage(int page, int size);
	
	/**
     * Tìm một video theo ID.
     *
     * @param id mã định danh của video
     * @return đối tượng {@link Video} nếu tìm thấy
     * @throws AppException nếu không tìm thấy hoặc xảy ra lỗi trong quá trình truy vấn
     */
	Video findById(String id);
	
	/**
     * Cập nhật thông tin của một video.
     *
     * @param video đối tượng {@link Video} chứa dữ liệu cần cập nhật
     * @throws AppException nếu xảy ra lỗi khi cập nhật
     */
	void update(Video video);
	
	void increaseViews(String videoId);

	void create(Video video);

	void delete(String id);
	
	List<Video> findAllByPage(int page, int size);
	
	HomeResponse getHomeData(int page, int size, User currentUser);
	
}
