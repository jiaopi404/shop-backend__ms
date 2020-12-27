package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
@Api(value = "上传文件", description = "上传文件 api")
public class FileUploadController {

    @PostMapping
    @ApiOperation("文件上传")
    public Result<String> upload (@RequestParam(value = "file")MultipartFile file) throws Exception {
        // 封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(), // 文件名
                file.getBytes(), // 文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename()) // 获取文件名字
        );
        // 调用 FastDFSUtil 工具类将文件传入到 fastDFS 中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);

        String url = "47.108.156.216:8081/" + uploads[0] + "/" + uploads[1];
        return new Result<>(true, StatusCode.OK, "上传成功", url);
    }
}
