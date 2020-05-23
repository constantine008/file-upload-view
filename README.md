### 上传接口
建一个springboot的web项目，然后写一个上传并返回文件路径的接口，接口代码如下：

```java
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
```
### 编辑配置文件
然后在配置文件修改一下静态路径的默认值就好，springboot默认的静态资源值是这些
`classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/`，我们只需要在这个值的后面加一个自己本地的路径就好，原来的静态路径能访问到，磁盘上的目录文件也能通过项目直接访问到。具体配置如下：

```yaml
server:
  port: 8888

#文件保存的磁盘路径
file:
  path: /Users/fxq/Desktop/tmp/temp/

spring:
  resources:
    static-locations: classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:${file.path}
```
### 接口测试
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200523152218223.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MDgyMzA0,size_16,color_FFFFFF,t_70)

### 图片预览

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020052315230353.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MDgyMzA0,size_16,color_FFFFFF,t_70)