package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public int saveImages(MultipartFile multipartFile, long projectId, boolean isTest) {
        String packageName = multipartFile.getOriginalFilename();                    //上传的包名
        int picNum=0;
        List<Image> list = new ArrayList<>();

        if(packageName.matches(".*\\.zip")){                //是zip压缩文件
            try{
                ZipInputStream zs = new ZipInputStream(multipartFile.getInputStream());
                BufferedInputStream bs = new BufferedInputStream(zs);
                ZipEntry ze;
                byte[] picture = null;
                while((ze = zs.getNextEntry()) != null){                    //获取zip包中的每一个zip file entry
                    String fileName = ze.getName();                            //pictureName
                    if(!(fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".JPG")||fileName.endsWith(".PNG")))
                        continue;
                    picture = new byte[(int) ze.getSize()];                    //读一个文件大小
                    bs.read(picture, 0, (int) ze.getSize());
                    Image image = new Image(projectId, picture, 1, 1, 0, false, isTest); //保存image

                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture));
                    image.setWidth(bufferedImage.getWidth());
                    image.setHeight(bufferedImage.getHeight());
                    list.add(image);
//					imrepository.saveAndFlush(image);
                    picNum++;
                }
                bs.close();
                zs.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }else if(packageName.matches(".*\\.rar")){                    //是rar压缩文件
            try {
                //MultipartFile file 转化为File 有临时文件产生：
                CommonsMultipartFile cf= (CommonsMultipartFile) multipartFile;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                File fs = fi.getStoreLocation();
                Archive archive = new Archive(fs);
                ByteArrayOutputStream bos = null;
                byte[] picture = null;
                FileHeader fh = archive.nextFileHeader();
                while(fh!=null){
                    String fileName = fh.getFileNameString();
                    if(!(fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".JPG")||fileName.endsWith(".PNG")))
                        continue;
                    bos = new ByteArrayOutputStream();
                    archive.extractFile(fh, bos);
                    picture = bos.toByteArray();
                    Image image = new Image(projectId, picture, 1, 1, 0, false, isTest); //保存image，非缩略图

                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture));
                    image.setWidth(bufferedImage.getWidth());
                    image.setHeight(bufferedImage.getHeight());
                    list.add(image);
//					imrepository.saveAndFlush(image);
                    picNum++;
                    fh = archive.nextFileHeader();
                }

                bos.close();
                archive.close();
            } catch (RarException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        imageRepository.saveAll(list);
        saveProjectOverview(projectId, list.get(0));

        imageRepository.flush();
        return picNum;
    }

    private void saveProjectOverview(long projectId, Image image) {
        String path = System.getProperty("user.dir")+"/projectOverview/pj" + projectId + ".jpg";
        try {
            Thumbnails.of(new ByteArrayInputStream(image.getPicture())).size(270, 210)
                    .outputFormat("jpg").toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
