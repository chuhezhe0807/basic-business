package com.chuhezhe.vo;

import lombok.Data;

import java.util.List;

/**
 * 检验返回给前端的vo
 *
 * ClassName: CheckResultVO
 * Package: com.chuhezhe.vo
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/20 23:32
 * @Version 1.0
 */
@Data
public class CheckResultVo {
    /**
     * 是否已上传
     */
    private Boolean uploaded;

    private String url;

    private List<Integer> uploadedChunks;
}
