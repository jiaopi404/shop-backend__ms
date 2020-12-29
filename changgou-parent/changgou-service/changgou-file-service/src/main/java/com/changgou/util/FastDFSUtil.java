package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

    /**
     * 获取文件信息; 文件在 Storage 中, 也要走一遍流程
     * @param groupName 组名
     * @param remoteFileName 文件的存储路径名字 M00/02/44/kldsfdlk.jpg
     */
    public static FileInfo getFile (String groupName, String remoteFileName) throws Exception {
//        // 创建 TrackerClient 通过 TrackerClient 对象返回 TrackerServer
//        TrackerClient trackerClient = new TrackerClient();
//
//        // 通过TrackerClient 获取 TrackerServer 的连接对象
//        TrackerServer trackerServer = trackerClient.getConnection();
//
//        // 通过 TrackerServer 获取 Storage 信息, 创建 StorageClient 对象存储 Storage 信息
//        StorageClient storageClient = new StorageClient(trackerServer, null);
        StorageClient storageClient = getStorageClient();

        // 获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    /**
     * 下载文件信息
     * @param groupName 组名
     * @param remoteFileName 文件的存储路径名字
     */
    public static InputStream downloadFile (String groupName, String remoteFileName) throws Exception {
        StorageClient storageClient = getStorageClient();

        byte[] downloadContent = storageClient.download_file(groupName, remoteFileName);

        return new ByteArrayInputStream(downloadContent);
    }

    /**
     * 文件删除
     * @param groupName 组名
     * @param remoteFileName 文件的存储路径名字
     * @throws Exception 异常
     */
    public static void deleteFile (String groupName, String remoteFileName) throws Exception {
        StorageClient storageClient = getStorageClient();

        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取 storage 信息
     * @return StorageServer
     * @throws Exception 异常
     */
    public static StorageServer getStorage () throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取 storage 的 ip 和 端口信息
     * @param groupName 组名
     * @param remoteFileName 文件的存储路径名
     * @return ServerInfo[] server 组
     * @throws Exception 异常
     */
    public static ServerInfo[] getServerInfo (String groupName, String remoteFileName) throws Exception {
        // 获取 storage 的 ip 和端口信息
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /**
     * 获取 tracker 信息
     * @return 返回路径
     * @throws Exception 异常
     */
    public static String getTrackerInfo () throws Exception {
        TrackerServer trackerServer = getTrackerServer();
        // 获取 配置文件中的 port 配置
        int tracker_http_port = ClientGlobal.getG_tracker_http_port();
        String hostname = trackerServer.getInetSocketAddress().getHostString();
        System.out.println(trackerServer.getInetSocketAddress().getPort());
        return "http://" + hostname + ":" + tracker_http_port;
    }

    /**
     * 获取 trackerServer
     * @return TrackerServer
     * @throws Exception
     */
    public static TrackerServer getTrackerServer () throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

    /**
     * 获取 storageClient
     * @return StorageClient
     * @throws Exception
     */
    public static StorageClient getStorageClient () throws Exception {
        return new StorageClient(getTrackerServer(), null);
    }

    public static void main(String[] args) throws Exception {
        // =============== 测试上传文件
//        // 读取文件内容
//        File file = new File("/Users/jiaopi404/Desktop/tmp/banner_1_3x.png");
//        InputStream is = new FileInputStream(file);
//        byte[] buffer = new byte[(int) file.length()]; // 创建数组
//        int offset = 0; // 偏移量
//        int numRead = 0; // 已读量
//        while (offset < buffer.length && (numRead = is.read(buffer, offset, buffer.length - offset)) >= 0) { // 读取
//            offset += numRead;
//        }
//        is.close(); // 关闭读取流
//        // 构造上传文件
//        FastDFSFile fastDFSFile = new FastDFSFile();
//        fastDFSFile.setName("测试上传文件");
//        fastDFSFile.setExt(StringUtils.getFilenameExtension(file.getName()));
//        fastDFSFile.setContent(buffer);
//        String[] uploads = upload(fastDFSFile);
//        System.out.println("上传成功， 返回： " + uploads[0] + "/" + uploads[1]);


        // =============== 测试获取文件信息
//        FileInfo fileInfo = getFile("group1", "M00/00/00/rBTTlV_oaOKAbU9HAAL7OvCscVY617.jpg");
//        System.out.println(fileInfo.toString());

        // =============== 测试下载文件
//        InputStream is = downloadFile("group1", "M00/00/00/rBTTlV_oaOKAbU9HAAL7OvCscVY617.jpg");
//
//        FileOutputStream fos = new FileOutputStream("/Users/jiaopi404/Desktop/tmp/1.jpg");
//        // 创建字节缓冲区
//        byte[] buffer = new byte[1024];
//        while (is.read(buffer) != -1) {
//            fos.write(buffer);
//        }
//        fos.flush(); // 清空缓冲区;
//        fos.close();
//        is.close();

        // ========== 获取 storage 信息
//        StorageServer storageServer = getStorage();
//        System.out.println(storageServer.getInetSocketAddress().getHostName()); // 47.108.156.216
//        System.out.println(storageServer.getInetSocketAddress().getAddress()); // 47.108.156.216/47.108.156.216
//        System.out.println(storageServer.getInetSocketAddress().getHostString()); // 47.108.156.216

        // =========== 获取 storage 组 的 ip 和端口信息;
//        ServerInfo[] serverInfos = getServerInfo("group1", "M00/00/00/rBTTlV_oaOKAbU9HAAL7OvCscVY617.jpg");
//        for (ServerInfo serverInfo:serverInfos) {
//            System.out.println(serverInfo.getIpAddr());
//            System.out.println(serverInfo.getPort());
//        }

        // ============ 测试获取 trackerInfo
        String trackerUrl = getTrackerInfo();
        System.out.println(trackerUrl);
    }
}
