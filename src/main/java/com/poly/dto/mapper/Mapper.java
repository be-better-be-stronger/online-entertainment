package com.poly.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.poly.dto.VideoDTO;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.ShareService;

public class Mapper {
	public static List<VideoDTO> toVideoDTOList(List<Video> videos, User currentUser, FavoriteService favoriteService,
			ShareService shareService) {
		List<VideoDTO> dtoList = new ArrayList<>();
		for (Video v : videos) {
			VideoDTO dto = toVideoDTO(v, currentUser, favoriteService, shareService);
			dtoList.add(dto);
		}
		return dtoList;
	}

	public static VideoDTO toVideoDTO(Video video, User currentUser, FavoriteService favoriteService,
			ShareService shareService) {
		boolean liked = false;
		if (currentUser != null) {
			liked = favoriteService.isVideoLikedByUser(currentUser.getId(), video.getId());
		}
		int likeCount = favoriteService.countByVideoId(video.getId());
		int shareCount = shareService.countByVideoId(video.getId());

		VideoDTO dto = new VideoDTO(video.getId(), video.getTitle(), video.getDescription(), 
				video.getLink(), video.getPoster(), video.getViews(), video.getCreatedDate(), liked, likeCount, shareCount);
		return dto;
	}
}
