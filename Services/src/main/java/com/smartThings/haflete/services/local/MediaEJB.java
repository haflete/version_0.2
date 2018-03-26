package com.smartThings.haflete.services.local;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.MediaRemote;
import com.smartThings.haflete.services.util.StartUp;

@Stateless(mappedName="ejb/MediaEJB", name="MediaEJB")
public class MediaEJB implements MediaRemote {

    private static final int THUMBNAIL_WIDTH = 150;
    
	@Override
	public ItemMedia createNewImage(ItemMedia media, Seller loginSeller) throws BusinessException {
		
		if(nullOrEmpty(media.getExt(),media.getName()))
			throw new BusinessException("fileNameCantBeNull");
		
		if(media.getContents().length < 10)
			throw new BusinessException("fileShouldntBeEmpty");
		
		String fullPath = createMediaPath(media, loginSeller);
		int endIndex = fullPath.lastIndexOf(File.separator);
	    String dirPath = fullPath.substring(0, endIndex);
	    
		media.setUrl(fullPath);
		
		try {
			if (Files.notExists(Paths.get(dirPath))) {
				Files.createDirectories(Paths.get(dirPath));
			}
			Files.write(Paths.get(fullPath), media.getContents());
			createThumbnail(fullPath, media.getName());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
		
		Item item = media.getItem();
		item.getMediaList().add(media);
		ItemDAO dao = new ItemDAO();
		dao.save(item);
		
		return media;
	}
	
	private void createThumbnail(String fullPath, String imageName) throws IOException {
		BufferedImage originalBufferedImage = null;
		originalBufferedImage = ImageIO.read(new File(fullPath));
	    
	    int widthToScale, heightToScale;
	    if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {
	     
	        heightToScale = (int)(1.1 * THUMBNAIL_WIDTH);
	        widthToScale = (int)((heightToScale * 1.0) / originalBufferedImage.getHeight() 
	                        * originalBufferedImage.getWidth());
	     
	    } else {
	        widthToScale = (int)(1.1 * THUMBNAIL_WIDTH);
	        heightToScale = (int)((widthToScale * 1.0) / originalBufferedImage.getWidth() 
	                        * originalBufferedImage.getHeight());
	    }
	    
	    BufferedImage resizedImage = new BufferedImage(widthToScale, 
	    	    heightToScale, originalBufferedImage.getType());
    	Graphics2D g = resizedImage.createGraphics();
    	 
    	g.setComposite(AlphaComposite.Src);
    	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	 
    	g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
    	g.dispose();
	    ImageIO.write(resizedImage, "JPG", new File(imageName + "_thumb"));

	}

	private boolean nullOrEmpty(String ... values) {
		for(String value : values)
			if(value == null || value.isEmpty())
				return true;
		return false;
	}
	
	private String createMediaPath(ItemMedia media, Seller loginSeller) {
		return StartUp.ROOT_PATH + File.separator + loginSeller.getUsername() + File.separator + media.getItem().getName()
				 + File.separator + media.getName();
	}
}
