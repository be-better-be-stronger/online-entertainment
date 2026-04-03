/**
 * Service interface for managing User entities.
 * Provides methods for user authentication, CRUD operations, and user queries with pagination and filtering.
 */
package com.poly.service;

import java.util.List;

import com.poly.entity.User;

public interface UserService {
	
	/**
	 * Lấy tất cả người dùng.
	 * 
	 * @return danh sách tất cả người dùng
	 */
	List<User> findAll();

	/**
	 * Xác thực người dùng dựa trên tên đăng nhập và mật khẩu.
	 * 
	 * @param username tên đăng nhập của người dùng
	 * @param password mật khẩu của người dùng
	 * @return người dùng đã được xác thực, hoặc null nếu xác thực thất bại
	 */
	User login(String username, String password);

	/**
	 * Lấy thông tin người dùng dựa trên tên đăng nhập.
	 * 
	 * @param username tên đăng nhập của người dùng
	 * @return người dùng có tên đăng nhập được chỉ định, hoặc null nếu không tìm thấy
	 */
	User getByUserName(String username);
	
	/**
	 * Tìm người dùng dựa trên địa chỉ email.
	 * 
	 * @param email địa chỉ email của người dùng
	 * @return người dùng có email được chỉ định, hoặc null nếu không tìm thấy
	 */
	User findByEmail(String email);

	/**
	 * Tạo một người dùng mới.
	 * 
	 * @param user người dùng cần tạo
	 */
	void create(User user);
	
	/**
	 * Cập nhật thông tin người dùng hiện có.
	 * 
	 * @param user người dùng cần cập nhật
	 */
	void update(User user);
	
	/**
	 * Xóa người dùng dựa trên ID của họ.
	 * 
	 * @param userId ID của người dùng cần xóa
	 */
	void delete(String userId);

	/**
	 * Lấy danh sách người dùng phân trang dựa trên vai trò của họ.
	 * 
	 * @param isAdmin xác định người dùng có phải là quản trị viên hay không
	 * @param page số trang cần lấy
	 * @param size số lượng người dùng trên mỗi trang
	 * @return danh sách người dùng phù hợp với tiêu chí
	 */
	List<User> findPageByRole(Boolean isAdmin, int page, int size);

	/**
	 * Đếm số lượng người dùng dựa trên vai trò của họ.
	 * 
	 * @param isAdmin xác định người dùng có phải là quản trị viên hay không
	 * @return số lượng người dùng phù hợp với tiêu chí
	 */
	int countByRole(Boolean isAdmin);

	/**
	 * Xóa người dùng, đảm bảo thao tác được thực hiện bởi một người dùng khác.
	 * 
	 * @param currentUser người dùng thực hiện thao tác xóa
	 * @param targetUserId ID của người dùng cần xóa
	 */
	void deleteUser(User currentUser, String targetUserId);

	/**
	 * Lấy danh sách người dùng phân trang dựa trên tên, email và vai trò của họ.
	 * 
	 * @param isAdmin xác định người dùng có phải là quản trị viên hay không
	 * @param keyword từ khóa tìm kiếm theo tên hoặc email
	 * @param page số trang cần lấy
	 * @param size số lượng người dùng trên mỗi trang
	 * @return danh sách người dùng phù hợp với tiêu chí
	 */
	List<User> findPageByNameOrEmailAndRole(Boolean isAdmin, String keyword, int page, int size);

	/**
	 * Đếm số lượng người dùng dựa trên tên, email và vai trò của họ.
	 * 
	 * @param isAdmin xác định người dùng có phải là quản trị viên hay không
	 * @param keyword từ khóa tìm kiếm theo tên hoặc email
	 * @return số lượng người dùng phù hợp với tiêu chí
	 */
	int countByNameOrEmailAndRole(Boolean isAdmin, String keyword);
}