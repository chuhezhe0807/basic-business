package com.chuhezhe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuhezhe.dto.FileChunkDto;
import com.chuhezhe.entity.FileStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件存储表(FileStorage)表服务层接口
 *
 * ClassName: FileStorageService
 * Package: com.chuhezhe.service
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 21:35
 * @Version 1.0
 */
public interface FileStorageService extends IService<FileStorage> {
    // 文件上传
    Boolean uploadFile(FileChunkDto dto);

    // 下载文件
    void downloadByIdentifier(String identifier, HttpServletRequest request, HttpServletResponse response);
}
