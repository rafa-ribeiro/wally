package com.warren.wally.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
	void read(MultipartFile file);
}
