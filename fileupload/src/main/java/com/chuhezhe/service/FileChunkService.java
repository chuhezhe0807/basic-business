package com.chuhezhe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuhezhe.dto.FileChunkDto;
import com.chuhezhe.entity.FileChunk;
import com.chuhezhe.vo.CheckResultVo;

/**
 * ClassName: FileChunkService
 * Package: com.chuhezhe.service
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 23:25
 * @Version 1.0
 */
public interface FileChunkService extends IService<FileChunk> {
    // 校验文件
    CheckResultVo check(FileChunkDto fileChunkDto);
}
