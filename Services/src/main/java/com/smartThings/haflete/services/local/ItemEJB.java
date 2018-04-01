package com.smartThings.haflete.services.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.util.BusinessException;
import com.smartThings.haflete.remoteServices.ItemRemote;
import com.smartThings.haflete.services.util.StartUp;

@Stateless(mappedName="ejb/ItemEJB", name="ItemEJB")
public class ItemEJB implements ItemRemote {
	ItemDAO dao = new ItemDAO();
	

	@Override
	public Item createFrontImg(Seller loginSeller, Item item, byte[] data) throws BusinessException {
		
		if(data == null || data.length < 10)
			throw new BusinessException("fileNameCantBeNull");
		
		String dir = createPathForCroppedImg(loginSeller, item);
		
		try {
			Files.write(Paths.get(dir), data);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
        item.setFrontImgUrl(findUrlFromDir(dir));
		dao.saveOrUpdate(item);
		
        return item;
	}
	
	private String createPathForCroppedImg(Seller loginSeller, Item item) {
		return StartUp.ROOT_PATH + File.separator + loginSeller.getUsername() + File.separator + item.getName() + 
				File.separator + item.getName() + ".jpg";
	}
	@Override
	public Item bringById(Long id) throws BusinessException {
		ItemDAO dao = new ItemDAO();
		return dao.bringById(Item.class, id);
	}

	@Override
	public Item createOrUpdate(Item item) throws BusinessException {
		dao.saveOrUpdate(item);
		return item;
	}
	
	private String findUrlFromDir(String dirPath) {
		return (File.separator + "media" + dirPath.replace(StartUp.ROOT_PATH, "")).replace("\\", "/");
	}
}
