import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConvertAllFileUtils {
    // 转换前路径
    public static final String img_url_pre_before = "https://cdn.jsdelivr.net//img/";
    // 转换后路径
    private final static String img_url_pre_after = "images/";
    // 文档路径
    private final static String docPath = "C:\\Users\\administered\\Documents\\GitHub\\study-notes\\";

    public static void main(String[] args) throws IOException {
        // 递归遍历目录以及子目录中的所有文件
        List<File> files = FileUtil.loopFiles(docPath);
        System.out.println("总文件数" + files.size());

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (File file : files) {
            if (FileNameUtil.isType(file.getName(), "md")) {
                System.out.println("MD 文件" + file.getName());
                executorService.submit(() -> {
                    FileReader fileReader = FileReader.create(file, Charset.forName("utf-8"));
                    String result = fileReader.readString().replaceAll(img_url_pre_before, img_url_pre_after);
                    System.out.println("转换完成");
                    try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(result);
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("error");
                    }
                });
            }
        }
    }
}
