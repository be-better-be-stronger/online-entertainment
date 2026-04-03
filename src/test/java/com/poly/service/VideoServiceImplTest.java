package com.poly.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.poly.dao.VideoDAO;
import com.poly.entity.Video;
import com.poly.service.impl.VideoServiceImpl;

public class VideoServiceImplTest {

    private VideoDAO videoDAO;
    private VideoService videoService;

    @BeforeEach
    void setup() {
        videoDAO = mock(VideoDAO.class); // Tạo mock DAO
        videoService = new VideoServiceImpl(); // Inject vào service
    }

    @Test
    void testGetTop6PopularVideos() {
        List<Video> videos = Arrays.asList(new Video(), new Video(), new Video(), new Video(), new Video(), new Video());
        when(videoDAO.findTop6ByViews()).thenReturn(videos);

        List<Video> result = videoService.getTop6PopularVideos();

        assertNotNull(result);
        assertEquals(6, result.size());
        verify(videoDAO).findTop6ByViews();
    }

    @Test
    void testGetTop6LatestVideos() {
        List<Video> videos = List.of(new Video(), new Video());
        when(videoDAO.findTop6ByLatest()).thenReturn(videos);

        List<Video> result = videoService.getTop6LatestVideos();

        assertEquals(2, result.size());
        verify(videoDAO).findTop6ByLatest();
    }

    @Test
    void testGetTotalVideos() {
        when(videoDAO.count()).thenReturn(42L);

        long count = videoService.getTotalVideos();

        assertEquals(42, count);
        verify(videoDAO).count();
    }

    @Test
    void testGetPage() {
        List<Video> videos = List.of(new Video());
        when(videoDAO.findAllActiveVideosByPage(1, 5)).thenReturn(videos);

        List<Video> result = videoService.findAllByPage(1, 5);

        assertEquals(1, result.size());
        verify(videoDAO).findAllByPage(0, 0);
    }

    @Test
    void testGetVideoByIdFound() {
        Video video = new Video();
        video.setId("v1");
        when(videoDAO.findById("v1")).thenReturn(video);

        Video result = videoService.findById("v1");

        assertNotNull(result);
        assertEquals("v1", result.getId());
        verify(videoDAO).findById("v1");
    }

	/*
	 * @Test void testGetVideoByIdNotFound() {
	 * when(videoDAO.findById("notfound")).thenReturn(null);
	 * 
	 * AppException ex = assertThrows(AppException.class, () -> {
	 * videoService.getVideoById("notfound"); });
	 * 
	 * assertTrue(ex.getMessage().contains("Không tìm thấy video với ID"));
	 * verify(videoDAO).findById("notfound"); }
	 */

	/*
	 * @Test void testUpdateVideo() { Video video = new Video();
	 * video.setId("v123");
	 * 
	 * videoService.updateVideo(video);
	 * 
	 * verify(videoDAO).update(video); }
	 */

    @Test
    void testIncreaseViews() {
        videoService.increaseViews("v999");

        verify(videoDAO).increaseViews("v999");
    }
}

