package com.smartThings.haflete.services.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.MediaRemote;
import com.smartThings.haflete.services.util.StartUp;

@Stateless(mappedName="ejb/MediaEJB", name="MediaEJB")
public class MediaEJB implements MediaRemote {

	@Override
	public ItemMedia createNewImage(ItemMedia media, Seller loginSeller) throws BusinessException {
		
		if(nullOrEmpty(media.getExt(),media.getName()))
			throw new BusinessException("fileNameCantBeNull");
		
		if(media.getContents().length < 10)
			throw new BusinessException("fileShouldntBeEmpty");
		
		String fullPath = createMediaPath(media, loginSeller);
		int endIndex = fullPath.lastIndexOf("\\\\");
	    String dirPath = fullPath.substring(0, endIndex);
	    
		media.setUrl(fullPath);
		
		try {
			if (Files.notExists(Paths.get(dirPath))) {
				Files.createDirectories(Paths.get(dirPath));
			}
			Files.write(Paths.get(fullPath), media.getContents());
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
	
	private boolean nullOrEmpty(String ... values) {
		for(String value : values)
			if(value == null || value.isEmpty())
				return true;
		return false;
	}
	
	private String createMediaPath(ItemMedia media, Seller loginSeller) {
		return StartUp.ROOT_PATH + StartUp.PATH_SPLITOR + loginSeller.getUsername() + StartUp.PATH_SPLITOR + media.getItem().getName()
				 + StartUp.PATH_SPLITOR + media.getName();
	}
}
