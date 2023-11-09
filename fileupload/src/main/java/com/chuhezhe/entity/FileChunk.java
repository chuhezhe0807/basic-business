package com.chuhezhe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件块存储(FileChunk)表实体类
 *
 * ClassName: FileChunk
 * Package: com.chuhezhe.entity
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 23:25
 * @Version 1.0
 */
@Data
@TableName("file_chunk")
public class FileChunk implements Serializable {
    private static final long serialVersionUID = 1697470883629L;

    /**主键**/
    @TableField(value = "id", jdbcType = JdbcType.INTEGER)
    private Long id;
    /**文件名**/
    @TableField(value = "file_name", jdbcType = JdbcType.VARCHAR)
    private String fileName;
    /**当前分片，从1开始**/
    @TableField(value = "chunk_number", jdbcType = JdbcType.INTEGER)
    private Integer chunkNumber;
    /**分片大小**/
    @TableField(value = "chunk_size", jdbcType = JdbcType.INTEGER)
    private Long chunkSize;
    /**当前分片大小**/
    @TableField(value = "current_chunk_size", jdbcType = JdbcType.INTEGER)
    private Long currentChunkSize;
    /**文件总大小**/
    @TableField(value = "total_size", jdbcType = JdbcType.INTEGER)
    private Long totalSize;
    /**总分片数**/
    @TableField(value = "total_chunk", jdbcType = JdbcType.INTEGER)
    private Integer totalChunk;
    /**文件标识 md5校验码**/
    @TableField(value = "identifier", jdbcType = JdbcType.VARCHAR)
    private String identifier;
    /**相对路径**/
    @TableField(value = "relative_path", jdbcType = JdbcType.VARCHAR)
    private String relativePath;

    /**创建者**/
    @TableField(value = "create_by", jdbcType = JdbcType.VARCHAR)
    private String createBy;
    /**创建时间**/
    @TableField(value = "create_time", jdbcType = JdbcType.TIMESTAMP)
    private LocalDateTime createTime;
    /**更新人**/
    @TableField(value = "update_by", jdbcType = JdbcType.VARCHAR)
    private String updateBy;
    /**更新时间**/
    @TableField(value = "update_time", jdbcType = JdbcType.TIMESTAMP)
    private LocalDateTime updateTime;
}
