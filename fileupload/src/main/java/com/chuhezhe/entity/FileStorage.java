package com.chuhezhe.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *文件存储表(FileStorage)表实体类
 *
 * ClassName: FileStorage
 * Package: com.chuhezhe.entity
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/19 23:49
 * @Version 1.0
 */
@Data
public class FileStorage implements Serializable {
    public static final long serialVersionUID = 202310172350L;

    // 主键
    private Long id;
    // 文件真实名称
    private String realName;
    // 文件名
    private String fileName;
    // 后缀名
    private String suffix;
    // 文件路径
    private String filePath;
    // 文件类型
    private String fileType;
    // 文件大小
    private Long size;
    // 文件校验码 md5
    private String identifier;
    /**创建者**/
    private String createBy;
    /**创建时间**/
    private LocalDateTime createTime;
    /**更新人**/
    private String updateBy;
    /**更新时间**/
    private LocalDateTime updateTime;
}
