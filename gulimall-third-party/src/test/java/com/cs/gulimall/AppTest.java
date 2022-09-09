package com.cs.gulimall;

import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {
    @Autowired
    private OSSClient ossClient;
    @Test
    public void testUpload() throws FileNotFoundException {
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "LTAI5tGiAaoP7sTWXnVd7z95";
//        String accessKeySecret = "3glPaqvKbo8m5Jm50B42HJYmyjTSFf";
//        //文件输入流上传文件
//        InputStream is=new FileInputStream("D:\\idm\\idm下载\\压缩文件\\谷粒学院\\docs\\pics\\919c850652e98031.jpg");
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "gulimall-chengs";
        // 文件名称
        String objectName = "hhhhh.jpg";

        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream is=new FileInputStream("D:\\idm\\idm下载\\压缩文件\\谷粒学院\\docs\\pics\\73366cc235d68202.jpg");
        ossClient.putObject(bucketName,objectName,is);
        //关闭OSSClient
        ossClient.shutdown();
        System.out.println("上传完成");
    }
}
