# 🎬 Online Entertainment - Java Web Project

> Một ứng dụng quản lý và chia sẻ video được xây dựng bằng JSP/Servlet, JPA/Hibernate và Bootstrap.

## 📌 Tính năng chính

- 🔐 Đăng nhập / Đăng ký người dùng
- 🧑‍💼 Phân quyền User / Admin
- ❤️ Like video
- 💬 Bình luận video (sắp phát triển)
- 📺 Xem video phổ biến, mới nhất
- 🔎 Tìm kiếm video theo tên
- 📊 Thống kê video được yêu thích theo năm
- 📨 Gửi email thông báo
- 🔧 CRUD video (Admin)
- 🧾 Phân trang danh sách video

## 🛠️ Công nghệ sử dụng

| Layer        | Công nghệ                         |
|--------------|-----------------------------------|
| Backend      | Java Servlet, JSP, JPA, Hibernate |
| Frontend     | JSP, HTML5, CSS3, Bootstrap 5     |
| CSDL         | MySQL                             |
| Build Tool   | Maven                             |
| Server       | Apache Tomcat 10                  |

## 📂 Cấu trúc thư mục

```
online-entertainment/
│
├── src/main/java/com/poly/
│   ├── controller/       # Servlet xử lý yêu cầu
│   ├── dao/              # DAO interfaces
│   ├── dao/impl/         # DAO triển khai
│   ├── entity/           # Các entity JPA
│   ├── service/          # Service interfaces
│   └── service/impl/     # Service triển khai
│
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── views/        # Các file JSP hiển thị
│   │   └── layout.jsp    # Layout chính
│   ├── css/              # File CSS
│   ├── js/               # File JS
│   └── images/           # Ảnh poster video
│
├── pom.xml               # Cấu hình Maven
└── persistence.xml       # Cấu hình JPA
```

## 🚀 Hướng dẫn chạy project

1. Clone repo:

   ```bash
   git clone https://github.com/<your-username>/online-entertainment.git
   ```

2. Import vào Eclipse hoặc IntelliJ dưới dạng **Maven Project**.

3. Cấu hình database MySQL:

   ```sql
   CREATE DATABASE online_entertainment;
   ```

4. Update `persistence.xml` với user/password của bạn.

5. Chạy project với Tomcat 10, truy cập:  
   👉 `http://localhost:8080/oe/home`

## 📷 Giao diện

> (Bạn có thể thêm ảnh minh hoạ ở đây)

---

## 📌 Tác giả

- **Đặng Quốc Thanh**  
- Java Web Developer @FPT Polytechnic

## 📄 License

This project is licensed under the MIT License.