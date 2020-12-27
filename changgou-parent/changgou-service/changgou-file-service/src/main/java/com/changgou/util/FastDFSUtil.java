package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 实现 FastDFS 文件操作;
 *   文件上传
 *   文件删除
 *   文件下载
 *   文件信息获取
 *   Storage 信息获取
 *   Tracker 信息获取
 *
 */
public class FastDFSUtil {
    /**
     * 加载 Tracker 连接信息;
     */
    static {
        try {
            // Spring BeanFactory 找到 classPath 下的文件
            // 加载 classPath 下的文件; ClassPathResource
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filePath); // conf_file 配置文件路径;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] upload (FastDFSFile file) throws Exception {
        // 附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        // 创建一个 tracker 访问的客户端对象 TrackerClient
        TrackerClient trackerClient = new TrackerClient();

        // 通过 TrackerClient 访问 TrackerServer 服务, 获取连接信息;
        TrackerServer trackerServer = trackerClient.getConnection();

        // 通过 TrackerServer 的连接信息 可以获取 Storage 的连接信息,
        // 创建 StorageClient 对象存储 Storage 的连接信息;
        StorageClient storageClient = new StorageClient(trackerServer, null);

        // 通过 StorageClient 访问 Storage, 实现文件上传, 并且获取文件长传后的存储信息;
        /**
         * 1. 上传文件的字节数组 byte[]
         * 2. 拓展名
         * 3. 附加参数; 如拍摄地址;
         * 响应: String[]
         * <ul><li>results[0]: the group name to store the file</li></ul>
         * <ul><li>results[1]: the new created filename</li></ul> M00/02/44/kldsfdlk.jpg
         */
        return storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
    }
}
