package com.warren.wally.file;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class FileUploadResolver {

	@Resource
	private FileUploadInvestimentos investimentos;

	@Resource
	private FileUploadMovimentos movimentos;

	public FileUpload resolve(TypeFile typeFile) {
		if (typeFile.equals(TypeFile.INVESTIMENTOS)) {
			return investimentos;
		}

		if (typeFile.equals(TypeFile.MOVIMENTOS)) {
			return movimentos;
		}
		throw new RuntimeException("Tipo de arquivo " + typeFile + " não tratado.");
	}
}
