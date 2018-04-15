package com.smartThings.haflete.services.local;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.dao.MediaDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.MediaRemote;
import com.smartThings.haflete.services.util.StartUp;

@Stateless(mappedName="ejb/MediaEJB", name="MediaEJB")
public class MediaEJB implements MediaRemote {

    private static final int THUMBNAIL_WIDTH = 300;
    private static final String COPY_RIGHT = "www.laylat3omor.com Copyright @ 2018";
    @Override
	public void deleteImage(long id) throws BusinessException {
    	MediaDAO dao = new MediaDAO();
    	ItemMedia item = dao.bringById(ItemMedia.class, id);
    	dao.delete(item);
    }
    
	@Override
	public ItemMedia createNewImage(ItemMedia media, Seller loginSeller) throws BusinessException {
		
		if(nullOrEmpty(media.getExt(),media.getName()))
			throw new BusinessException("fileNameCantBeNull");
		
		if(media.getContents().length < 10)
			throw new BusinessException("fileShouldntBeEmpty");
		
		String fullPath = createMediaPath(media, loginSeller);
		int endIndex = fullPath.lastIndexOf(File.separator);
	    String dirPath = fullPath.substring(0, endIndex);
	    String fileName = media.getName().substring(0, media.getName().indexOf("."));
		String ext = media.getName().substring(media.getName().indexOf(".") + 1);
		
	    media.setFullDir(fullPath);
	    media.setUrl(findUrlFromDir(fullPath));
		media.setThumbFullDir(dirPath + File.separator + fileName + "_thumb." + ext);
		media.setThumbUrl(findUrlFromDir(media.getThumbFullDir()));
		
		try {
			if (Files.notExists(Paths.get(dirPath))) {
				Files.createDirectories(Paths.get(dirPath));
			}
			Files.write(Paths.get(fullPath), media.getContents());
			createThumbnail(fullPath, media.getThumbFullDir(), ext);
			waterMarkImage(fullPath, ext);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
		
		Item item = media.getItem();
		item.getMediaList().add(media);
		ItemDAO dao = new ItemDAO();
		dao.saveOrUpdate(item);
		
		return media;
	}

	private void waterMarkImage(String fullPath, String ext) {
		 ImageIcon icon = new ImageIcon(fullPath);
		 
         // create BufferedImage object of same width and height as of original image
         BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
                     icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);

         // create graphics object and add original image to it
         Graphics graphics = bufferedImage.getGraphics();
         graphics.drawImage(icon.getImage(), 0, 0, null);

         // set font for the watermark text
         graphics.setFont(new Font("Arial", Font.BOLD, 30));
         graphics.setColor(new Color(0, 0, 0));
         
         //unicode characters for (c) is \u00a9
         String watermark = "\u00a9 http://laylat3omor.com";

         // add the watermark text
         graphics.drawString(watermark, 0, icon.getIconHeight() - 20);
         graphics.dispose();

         File newFile = new File(fullPath);
         try {
               ImageIO.write(bufferedImage, ext, newFile);
         } catch (IOException e) {
               e.printStackTrace();
         }
	}

	private void createThumbnail(String fullPath, String thumbFullPath, String ext) throws IOException {
		BufferedImage originalBufferedImage = null;
		originalBufferedImage = ImageIO.read(new File(fullPath));
	    
	    int widthToScale, heightToScale;
//	    if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {
//	        heightToScale = THUMBNAIL_WIDTH;
//	        widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight() 
//	                        * originalBufferedImage.getWidth());
//	    } else {
	        widthToScale = THUMBNAIL_WIDTH;
	        heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth() 
	                        * originalBufferedImage.getHeight());
//	    }
	    
	    BufferedImage resizedImage = new BufferedImage(widthToScale, 
	    	    heightToScale, originalBufferedImage.getType());
    	Graphics2D g = resizedImage.createGraphics();
    	 
    	g.setComposite(AlphaComposite.Src);
    	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	 
    	g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
    	g.dispose();
    	File image = new File(thumbFullPath);
	    ImageIO.write(resizedImage, ext, image);

	}
	
	@Override
	public ItemMedia createNewVideo(ItemMedia media, Seller loginSeller) throws BusinessException {
		if(nullOrEmpty(media.getExt(),media.getName()))
			throw new BusinessException("fileNameCantBeNull");
		
		if(media.getContents().length < 10)
			throw new BusinessException("fileShouldntBeEmpty");
		
		String fullPath = createMediaPath(media, loginSeller);
		int endIndex = fullPath.lastIndexOf(File.separator);
	    String dirPath = fullPath.substring(0, endIndex);
	    
	    media.setFullDir(fullPath);
	    media.setUrl(findUrlFromDir(fullPath));
	    
	    String fileName = media.getName().substring(0, media.getName().lastIndexOf("."));
	    String thumbFullDir = dirPath + File.separator + fileName + ".jpg";
	    
		try {
			if (Files.notExists(Paths.get(dirPath))) {
				Files.createDirectories(Paths.get(dirPath));
			}
			Files.write(Paths.get(fullPath), media.getContents());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
	    
		try {
			generateVideoThumb(fullPath, thumbFullDir);
			media.setThumbUrl(findUrlFromDir(thumbFullDir));
		} catch (IOException | InterruptedException | Exception e1) {
			media.setThumbUrl(findUrlFromDir(StartUp.DEFAULT_VIDEO_IMG_PATH));
			e1.printStackTrace();
		}
	    
		Item item = media.getItem();
		item.getMediaList().add(media);
		ItemDAO dao = new ItemDAO();
		dao.saveOrUpdate(item);
		
		return media;
	}
	
	public void generateVideoThumb(String fullDir, String thumbDir)
		      throws IOException, InterruptedException, Exception {
		
		FFmpegFrameGrabber g = new FFmpegFrameGrabber(fullDir);
        g.setFormat("mp4");
        g.start();

        for (int i = 0 ; i < 50 ; i++) {
            ImageIO.write(g.grab().getBufferedImage(), "jpg", new File(thumbDir));
        }

         g.stop();
    }
	
	@Override
	public String findThumbVideoUrl() {
		return findUrlFromDir(StartUp.DEFAULT_VIDEO_IMG_PATH);
	}
	
	private String findUrlFromDir(String dirPath) {
		return (File.separator + "media" + dirPath.replace(StartUp.ROOT_PATH, "")).replace("\\", "/");
	}

	private boolean nullOrEmpty(String ... values) {
		for(String value : values)
			if(value == null || value.isEmpty())
				return true;
		return false;
	}
	
	private String createMediaPath(ItemMedia media, Seller loginSeller) {
		return folderContainerpDir(media, loginSeller) + File.separator + media.getName();
	}
	
	private String folderContainerpDir(ItemMedia media, Seller loginSeller) {
		return StartUp.ROOT_PATH + File.separator + loginSeller.getUsername() + File.separator + media.getItem().getName();
	}

}
