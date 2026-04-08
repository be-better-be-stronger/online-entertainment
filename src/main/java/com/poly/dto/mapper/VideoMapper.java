package com.poly.dto.mapper;

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
}
