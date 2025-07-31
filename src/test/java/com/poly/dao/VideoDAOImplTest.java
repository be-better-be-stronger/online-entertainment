package com.poly.dao;

import com.poly.dao.impl.VideoDAOImpl;
import com.poly.entity.Video;
import com.poly.exception.AppException;
import com.poly.utils.JpaUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoDAOImplTest {

    private VideoDAO videoDAO;

    @BeforeAll
    static void setupAll() {
        System.setProperty("app.env", "test"); // dùng persistence-unit testOE
        JpaUtil.getEntityManager(); // init EMF
    }

    @BeforeEach
    void setup() {
        videoDAO = new VideoDAOImpl();

        // Thêm dữ liệu mẫu
        for (int i = 1; i <= 10; i++) {
            Video v = new Video();
            v.setId("vid" + i);
            v.setTitle("Video " + i);
            v.setViews(100 - i * 5); // giảm dần
            v.setActive(true);

            videoDAO.create(v);
        }
    }

    @AfterEach
    void cleanup() {
        // Xóa toàn bộ dữ liệu
        List<Video> all = videoDAO.findAll(0, 100);
        for (Video v : all) {
            videoDAO.delete(v.getId());
        }
    }

    @AfterAll
    static void tearDownAll() {
        JpaUtil.close();
    }

    @Test
    void testFindTop6ByViews() {
        List<Video> result = videoDAO.findTop6ByViews();
        assertNotNull(result);
        assertEquals(6, result.size());
        assertTrue(result.get(0).getViews() >= result.get(1).getViews());
    }

    @Test
    void testFindTop6ByLatest() {
        List<Video> result = videoDAO.findTop6ByLatest();
        assertNotNull(result);
        assertEquals(6, result.size());
    }

    @Test
    void testFindAllWithPagination() {
        List<Video> page1 = videoDAO.findAll(0, 5);
        List<Video> page2 = videoDAO.findAll(1, 5);
        assertEquals(5, page1.size());
        assertEquals(5, page2.size());
    }

    @Test
    void testCount() {
        long count = videoDAO.count();
        assertEquals(10, count);
    }

    @Test
    void testFindByIdFound() {
        Video v = videoDAO.findById("vid1");
        assertNotNull(v);
        assertEquals("vid1", v.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Video v = videoDAO.findById("invalid");
        assertNull(v);
    }

    @Test
    void testCreateAndDelete() {
        Video v = new Video();
        v.setId("newId");
        v.setTitle("New Video");
        v.setViews(123);
        v.setActive(true);

        videoDAO.create(v);
        assertNotNull(videoDAO.findById("newId"));

        videoDAO.delete("newId");
        assertNull(videoDAO.findById("newId"));
    }

    @Test
    void testUpdate() {
        Video v = videoDAO.findById("vid1");
        v.setTitle("Updated Title");
        videoDAO.update(v);

        Video updated = videoDAO.findById("vid1");
        assertEquals("Updated Title", updated.getTitle());
    }

    @Test
    void testIncreaseViews() {
        Video v = videoDAO.findById("vid1");
        int oldViews = v.getViews();

        videoDAO.increaseViews("vid1");

        Video updated = videoDAO.findById("vid1");
        assertEquals(oldViews + 1, updated.getViews());
    }

    @Test
    void testIncreaseViewsInvalidId() {
        Exception ex = assertThrows(AppException.class, () -> {
            videoDAO.increaseViews("not-found");
        });
        System.out.println("Message: " + ex.getMessage());
        assertTrue(ex.getMessage().contains("Không tìm thấy video để tăng views"));
    }
}
