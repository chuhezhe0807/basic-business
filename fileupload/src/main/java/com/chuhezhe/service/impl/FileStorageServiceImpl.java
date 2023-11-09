package com.chuhezhe.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuhezhe.dto.FileChunkDto;
import com.chuhezhe.entity.FileChunk;
import com.chuhezhe.entity.FileStorage;
import com.chuhezhe.mapper.FileStorageMapper;
import com.chuhezhe.service.FileChunkService;
import com.chuhezhe.service.FileStorageService;
import com.chuhezhe.util.ChuhezheFileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * 文件存储表(FileStorage)表服务层接口实现类
 *
 * ClassName: FileStorageServiceImpl
 * Package: com.chuhezhe.service.impl
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 22:23
 * @Version 1.0
 */

// FileStorageService 中的部分接口由 ServiceImpl<BaseMapper, entity> 实现了
// ServiceImpl<BaseMapper> BaseMapper 是为了把我们自己实现的Mapper 表数据访问层对象注入
@Service("fileStorageService")
@Slf4j
public class FileStorageServiceImpl extends ServiceImpl<FileStorageMapper, FileStorage> implements FileStorageService {
    // 默认的分块的大小
    @Value("${file.chunk-size}")
    public Long defaultChunkSize;

    // 上传地址
    @Value("${file.path}")
    public String baseFileSavePath;

    // @Resource(java标准) byName自动装配，可指定name、type，@Autowied(spring) byType自动装配
    @Resource
    public FileChunkService fileChunkService;

    @Override
    public Boolean uploadFile(FileChunkDto dto) {
        // TODO 校验文件 考虑 /resources/static/file-upload.png
        if(dto.getFile() == null) {
            throw new RuntimeException("文件不能为空");
        }

        String fullFileName = baseFileSavePath + File.separator + dto.getFilename();
        Boolean uploadFlag;

        // 如果是单文件上传
        if(dto.getTotalChunks() == 1) {
            uploadFlag = this.uploadSingleFile(fullFileName, dto);
        }
        else {
            // 分片上传
            uploadFlag = this.uploadSharding(fullFileName, dto);
        }

        // 如果本次上传成功则存储数据到表中
        if(uploadFlag) {
            this.saveFile(fullFileName, dto);
        }

        return uploadFlag;
    }

    @Override
    public void downloadByIdentifier(String identifier, HttpServletRequest request, HttpServletResponse response) {
        FileStorage fileStorage = this.getOne(new LambdaQueryWrapper<FileStorage>()
                .eq(FileStorage::getIdentifier, identifier));

        if(BeanUtil.isNotEmpty(fileStorage)) {
            File toFile = new File(baseFileSavePath + File.separator + fileStorage.getFilePath());

            try {
                ChuhezheFileUtil.downloadFile(request, response, toFile);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
        }
        else {
            throw new RuntimeException("文件不存在");
        }
    }

    // 单文件上传
    // Exception异常有两个分支RuntimeException和编译时异常，前者不捕获，后者一定要捕获不然编译时会报错
    // @SneakyThrows 利用这一机制将将当前方法抛出的异常包装成RuntimeException，骗过编译器。
    // 这个方法在本项目中一直都是只有一个地方调用的，并最终try catch了异常
    @SneakyThrows
    private Boolean uploadSingleFile(String fullFileName, FileChunkDto dto) {
        File file = new File(fullFileName);
        dto.getFile().transferTo(file);

        return Boolean.TRUE;
    }

    // 分片上传
    private Boolean uploadSharding(String fullFileName, FileChunkDto dto) {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fullFileName, "rw")) {
            long chunkSize = dto.getChunkSize() == 0L ? defaultChunkSize : dto.getChunkSize().longValue();
            // 计算偏移量
            long offset = chunkSize * (dto.getChunkNumber() - 1);
            // 定位到offset的位置
            randomAccessFile.seek(offset);
            // 写入
            randomAccessFile.write(dto.getFile().getBytes());
        }
        catch(IOException e) {
            log.error("文件上传失败" + e);

            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void saveFile(String fileName, FileChunkDto dto) {
        FileChunk chunk = BeanUtil.copyProperties(dto, FileChunk.class);
        chunk.setFileName(dto.getFilename());
        chunk.setTotalChunk(dto.getTotalChunks());

        fileChunkService.save(chunk);

        // 如果所有的分片都上传完成，那么也应该在文件记录表中存储一份数据
        // TODO 这里最好每次上传完成都存一下缓存，从缓存中查看是否所有块上传完成，这里偷懒了, 暂时不知道怎么实现 20231022
        if(dto.getChunkNumber().equals(dto.getTotalChunks())) {
            String name = dto.getFilename();
            MultipartFile file = dto.getFile();

            FileStorage fileStorage = new FileStorage();
            fileStorage.setRealName(file.getOriginalFilename());
            fileStorage.setFileName(fileName);
            fileStorage.setSuffix(FileUtil.getSuffix(name));
            fileStorage.setFileType(file.getContentType());
            fileStorage.setSize(dto.getTotalSize());
            fileStorage.setIdentifier(dto.getIdentifier());
            fileStorage.setFilePath(dto.getRelativePath());

            this.save(fileStorage);
        }
    }
}
