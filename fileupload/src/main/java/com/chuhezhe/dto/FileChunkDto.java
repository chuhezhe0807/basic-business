package com.chuhezhe.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 接受前端传递过来的参数
 * ClassName: FileChunkDto
 * Package: com.chuhezhe.dto
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 21:39
 * @Version 1.0
 */
@Data
public class FileChunkDto {
    // 当前分片（块）的次序，第一块是1.
    private Integer chunkNumber;
    // 文件分块的总数
    private Integer totalChunks;
    // 分块大小，最后一块可能比这个值大
    private Long chunkSize;
    // 当前块的实际大小
    private Long currentChunkSize;
    // 文件总大小
    private Long totalSize;
    // 文件唯一标识
    private String identifier;
    // 文件名
    private String filename;
    // 文件上传的时候文件的相对路径
    private String relativePath;
    // 文件
    private MultipartFile file;
}
