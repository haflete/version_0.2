package com.smartThings.haflete.services.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.ItemMedia;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.ItemRemote;

public class ItemEJB implements ItemRemote {
	
	@Override
	public Item createFrontImg(Item item) throws BusinessException {
		ItemMedia media = item.getChoosenImage();
		
		if(nullOrEmpty(media.getExt(),media.getName()))
			throw new BusinessException("fileNameCantBeNull");
		
		if(media.getContents().length < 10)
			throw new BusinessException("fileShouldntBeEmpty");
		
		try {
			Files.write(Paths.get(media.getUrl()), media.getContents());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
        
		ItemDAO dao = new ItemDAO();
		dao.saveOrUpdate(item);
		
        return item;
	}
	
	private boolean nullOrEmpty(String ... values) {
		for(String value : values)
			if(value == null || value.isEmpty())
				return true;
		return false;
	}
}
