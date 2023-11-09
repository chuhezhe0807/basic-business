package com.chuhezhe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuhezhe.entity.FileStorage;

/**
 * 文件存储表(FileStorage)表数据库访问层
 *
 * ClassName: FileStorageMapper
 * Package: com.chuhezhe.mapper
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 22:26
 * @Version 1.0
 */

// extends BaseMapper<> 后，MybatisPlus就会帮我们把BaseMapper里面的接口全部实现代理，不用再写xml文件了
public interface FileStorageMapper extends BaseMapper<FileStorage> {
}
