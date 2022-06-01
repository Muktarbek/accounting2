package com.peaksoft.accounting.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file);

    String deleteFile(String fileName);

    byte[] downloadFile(String fileName);
}
