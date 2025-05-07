package com.cs.campsite.member.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ThumbnailUtil {

	public static void createThumbnail(File originalFile, int width, int height) throws IOException {
		String thumbnailPath = originalFile.getParent() + "/thumb_" + originalFile.getName();
		Thumbnails.of(originalFile).size(width, height).toFile(new File(thumbnailPath));
	}
}
