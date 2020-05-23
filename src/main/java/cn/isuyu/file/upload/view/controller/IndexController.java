package cn.isuyu.file.upload.view.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/5/14 下午3:06
 */
@RestController
public class IndexController {

    /**
     * 本地保存路径
     */
    @Value("${file.path}")
    private String dirPath;

    @RequestMapping(value = "upload")
    public String upload(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        //文件后缀
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + prefix;
        Files.copy(inputStream,new File(dirPath + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);

        //拼接上传文件路径
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        return fileDownloadUri;
    }
}
