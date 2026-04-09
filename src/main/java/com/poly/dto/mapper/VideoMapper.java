package com.poly.dto.mapper;

import java.util.List;
import java.util.Map;

import com.poly.dto.VideoDTO;
import com.poly.entity.Video;

public class VideoMapper {
	public static VideoDTO toVideoDTO(
			Video video, 
			boolean liked, 
			long likeCount, 
			long shareCount) {

		if (video == null) {
			return null; 
		}

		return new VideoDTO(
				video.getId(), 
				video.getTitle(), 
				video.getDescription(), 
				video.getLink(),
				video.getPoster(),
				video.getViews(), 
				video.getCreatedDate(), 
				
				liked, 
				likeCount, 
				shareCount
		);
	}
	
	public static List<VideoDTO> mapList(
            List<Video> videos,
            Map<String, Boolean> likedMap,
            Map<String, Long> likeCountMap,
            Map<String, Long> shareCountMap
    ) {
        return videos.stream()
                .map(v -> VideoMapper.toVideoDTO(
                        v,
                        likedMap.getOrDefault(v.getId(), false),
                        likeCountMap.getOrDefault(v.getId(), 0L),
                        shareCountMap.getOrDefault(v.getId(), 0L)
                ))
                .toList();
    }
}
