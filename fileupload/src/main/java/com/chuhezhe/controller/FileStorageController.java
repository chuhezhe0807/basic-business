package com.chuhezhe.controller;

import com.chuhezhe.common.Res;
import com.chuhezhe.common.Result;
import com.chuhezhe.dto.FileChunkDto;
import com.chuhezhe.service.FileChunkService;
import com.chuhezhe.service.FileStorageService;
import com.chuhezhe.vo.CheckResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * ClassName: FileStorageController
 * Package: com.chuhezhe.controller
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/22 16:36
 * @Version 1.0
 */
@RestController
@RequestMapping("fileStorage")
public class FileStorageController {
    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private FileChunkService fileChunkService;

    // 校验接口，上传前现根据本接口查询一下 服务器是否存在该文件
    @GetMapping("/upload")
    public Result<CheckResultVo> checkUpload(FileChunkDto dto) {return Res.ok(fileChunkService.check(dto));}

    /**
     * 文件上传接口
     * @param dto 前端传递的参数
     * @param response response 配合前端返回响应的状态码
     * @return boolean
     */
    @PostMapping("/upload")
    public Result<Boolean> upload(FileChunkDto dto, HttpServletResponse response) {
        try {
            Boolean status = fileStorageService.uploadFile(dto);

            if(status) {
                return Res.ok();
            }
            else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                return Res.error("上传失败!");
            }
        }
        catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            return Res.error();
        }
    }

    // 下载接口
    @GetMapping("/download/{identifier}")
    public void downloadByIdentifier(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable("identifier") String identifier) {
        fileStorageService.downloadByIdentifier(identifier, request, response);
    }

    /**
     * Test
     */
    @GetMapping("/test")
    public String testReq(HttpServletRequest request, HttpServletResponse response) {

        String reqStr1 = request.getMethod() + "<br />" +
                request.getQueryString() + "<br />" +
                request.getRequestURI() + "<br />" +
                request.getRequestURL() + "<br />";

        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())  {          //读取请求消息头
            String name = headerNames.nextElement();
            sb.append(name).append(":").append(request.getHeader(name)).append("<br />");
        }

        StringBuilder cookieStr = new StringBuilder();

        if(request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookieStr.append("cookie: ")
                        .append(cookie.getName())
                        .append(" value: ")
                        .append(cookie.getValue())
                        .append("<br />");
            }
        }

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        return reqStr1 + "<br />" + sb + "<br />" + cookieStr;
    }
}
