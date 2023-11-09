package com.chuhezhe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chuhezhe.dto.FileChunkDto;
import com.chuhezhe.entity.FileChunk;
import com.chuhezhe.mapper.FileChunkMapper;
import com.chuhezhe.service.FileChunkService;
import com.chuhezhe.vo.CheckResultVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件块（FileChunk）表服务实现类
 *
 * ClassName: FileChunkServiceImpl
 * Package: com.chuhezhe.service.impl
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 23:30
 * @Version 1.0
 */
@Service("FileChunkService")
public class FileChunkServiceImpl extends ServiceImpl<FileChunkMapper, FileChunk> implements FileChunkService {
    @Override
    public CheckResultVo check(FileChunkDto fileChunkDto) {
        CheckResultVo checkResultVo = new CheckResultVo();

        // 1、根据 identifier 查找数据是否存在
        List<FileChunk> list = this.list(new LambdaQueryWrapper<FileChunk>()
                .eq(FileChunk::getIdentifier, fileChunkDto.getIdentifier())
                .orderByAsc(FileChunk::getChunkNumber)
        );

        // 2、判断list元素是否为空，如果为空返回没有上传该文件，如果不为空继续判断上传的分片是否全部上传完成
        if(list.size() == 0) {
            checkResultVo.setUploaded(false);

            return checkResultVo;
        }

        // 2.1 totalChunk为1，文件没有分片，已经完成了全部上传
        FileChunk fileChunk = list.get(0);
        if(fileChunk.getTotalChunk() == 1) {
            checkResultVo.setUploaded(true);

            return checkResultVo;
        }

        // 2.2 totalChunk为1，文件分片，返回已经上传了的分片
        ArrayList<Integer> uploadedFileChunks = new ArrayList<>();

        for(FileChunk chunk : list) {
            uploadedFileChunks.add(chunk.getChunkNumber());
        }

        checkResultVo.setUploadedChunks(uploadedFileChunks);

        // 3、返回结果VO
        return checkResultVo;
    }
}
