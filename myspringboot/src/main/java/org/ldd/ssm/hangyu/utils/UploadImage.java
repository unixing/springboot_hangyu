package org.ldd.ssm.hangyu.utils;
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;
import java.io.IOException;  
  


import javax.imageio.ImageIO;  
    
public class UploadImage {
      
//    public static void main(String[] args) {  
//        zipWidthHeightImageFile(new File("C:\\model\\我的图片.jpg"),new File("C:\\model\\我的图片-1.jpg"),118,118,0.7f);
//        
//        zipWidthHeightImageFile(new File("C:\\model\\我的图片.jpg"),new File("C:\\model\\我的图片-2.jpg"),220,150,0.7f);
//          
//        //zipImageFile(new File("C:\\spider\\2.JPG"),new File("C:\\spider\\2-2.JPG"),425,638,0.7f);  
//          
//        //zipImageFile(new File("C:\\spider\\3.jpg"),new File("C:\\spider\\3-3.jpg"),425,638,0.7f);  
//          
//    }  
    
    /**  
     * 根据设置的宽高等比例压缩图片文件<br> 先保存原文件，再压缩、上传  
     * @param oldFile  要进行压缩的文件  
     * @param newFile  新文件  
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）  
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）  
     * @param quality 质量  
     * @return 返回压缩后的文件的全路径  
     */    
    public static String zipImageFile(File oldFile,File newFile, int width, int height,float quality) {    
        if (oldFile == null) {    
            return null;    
        }    
        try {    
            /** 对服务器上的临时文件进行处理 */    
            Image srcFile = ImageIO.read(oldFile);    
            int w = srcFile.getWidth(null);    
            int h = srcFile.getHeight(null);    
            double bili;    
            if(width>0){    
                bili=width/(double)w;    
                height = (int) (h*bili);    
            }else{    
                if(height>0){    
                    bili=height/(double)h;    
                    width = (int) (w*bili);    
                }    
            }    
              
            String srcImgPath = newFile.getAbsoluteFile().toString();  
            String subfix = "jpg";  
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());  
  
            BufferedImage buffImg = null;   
            if(subfix.equals("png")){  
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  
            }else{  
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            }  
  
            Graphics2D graphics = buffImg.createGraphics();  
            graphics.setBackground(new Color(255,255,255));  
            graphics.setColor(new Color(255,255,255));  
            graphics.fillRect(0, 0, width, height);  
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);    
  
            ImageIO.write(buffImg, subfix, new File(srcImgPath));    
    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        return newFile.getAbsolutePath();    
    }    
    
    /**  
     * 按设置的宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传  
     * @param oldFile  要进行压缩的文件全路径  
     * @param newFile  新文件  
     * @param width  宽度  
     * @param height 高度  
     * @param quality 质量  
     * @return 返回压缩后的文件的全路径  
     */    
    public static boolean zipWidthHeightImageFile(File oldFile,File newFile, int width, int height,float quality) {    
		if (oldFile == null) {
			return false;
		}
//		String newImage = null;
		boolean result = false;
		try {
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(oldFile);
			//删除源文件
			oldFile.delete();
			String srcImgPath = newFile.getAbsoluteFile().toString();
			String subfix = "jpg";
			subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1,
					srcImgPath.length());
			BufferedImage buffImg = null;
			if (subfix.equals("png")) {
				buffImg = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB);
			} else {
				buffImg = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
			}
			Graphics2D graphics = buffImg.createGraphics();
			graphics.setBackground(new Color(255, 255, 255));
			graphics.setColor(new Color(255, 255, 255));
			graphics.fillRect(0, 0, width, height);
			graphics.drawImage(srcFile.getScaledInstance(width, height,
					Image.SCALE_SMOOTH), 0, 0, null);
			ImageIO.write(buffImg, subfix, new File(srcImgPath));
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
		return result;   
    }
    public static boolean copyTheFile(File oldFile,File newFile){
    	int byteread = 0;
    	byte[] buffer = new byte[1024];
    	FileInputStream is;
    	FileOutputStream os;
    	boolean result = false;
		try {
			if(oldFile.exists()){
				is = new FileInputStream(oldFile);
				os = new FileOutputStream(newFile);
				while ((byteread = is.read(buffer))!=-1) {
					os.write(buffer,0,byteread);
				}
				is.close();
				os.close();
				result = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
		return result;
    }
}
