package com.poly.dto.mapper;

import java.util.List;
import java.util.Map;

import com.poly.dto.VideoDTO;
import com.poly.entity.Video;

public class VideoMapper {
	public static VideoDTO toVideoDTO(
			Video video, 
			boolean liked, 
			int likeCount, 
			int shareCount) {

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
            Map<String, Integer> likeCountMap,
            Map<String, Integer> shareCountMap
    ) {
        return videos.stream()
                .map(v -> VideoMapper.toVideoDTO(
                        v,
                        likedMap.getOrDefault(v.getId(), false),
                        likeCountMap.getOrDefault(v.getId(), 0),
                        shareCountMap.getOrDefault(v.getId(), 0)
                ))
                .toList();
    }
}
