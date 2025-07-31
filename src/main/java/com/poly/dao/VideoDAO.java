package com.poly.dao;

import java.util.List;

import com.poly.entity.Video;

public interface VideoDAO {
	
	/**
     * Trả về danh sách 6 video có lượt xem nhiều nhất.
     *
     * @return danh sách top 6 video phổ biến theo lượt xem, sắp xếp giảm dần
     */
	List<Video> findTop6ByViews();		
	
	/**
     * Trả về danh sách 6 video mới nhất.
     *
     * @return danh sách top 6 video được đăng gần đây nhất, sắp xếp theo ngày tạo giảm dần
     */
	List<Video> findTop6ByLatest();	
           
	 /**
     * Lấy danh sách video theo phân trang.
     *
     * @param page chỉ số trang (bắt đầu từ 0)
     * @param size số lượng video trên mỗi trang
     * @return danh sách video tương ứng với trang và kích thước đã chỉ định
     */
    List<Video> findAll(int page, int size);  
    
    /**
     * Đếm tổng số video trong cơ sở dữ liệu.
     *
     * @return tổng số bản ghi video
     */
    long count();    
    

    /**
     * Tìm video theo ID.
     *
     * @param id mã định danh của video
     * @return đối tượng {@link Video} nếu tìm thấy, ngược lại trả về {@code null}
     * @throws AppException nếu xảy ra lỗi trong quá trình truy vấn
     */
    Video findById(String id);
    
    /**
     * Thêm một video mới vào cơ sở dữ liệu.
     *
     * @param video đối tượng {@link Video} cần được lưu
     * @throws AppException nếu xảy ra lỗi khi lưu video
     */
    void create(Video video);
    
    /**
     * Cập nhật thông tin của một video đã tồn tại.
     *
     * @param video đối tượng {@link Video} với dữ liệu đã chỉnh sửa
     * @throws AppException nếu xảy ra lỗi khi cập nhật video
     */
    void update(Video video);
    
    /**
     * Xóa một video theo ID.
     *
     * @param id mã định danh của video cần xóa
     * @throws AppException nếu xảy ra lỗi trong quá trình xóa
     */
    void delete(String id);

	void increaseViews(String videoId);
}
