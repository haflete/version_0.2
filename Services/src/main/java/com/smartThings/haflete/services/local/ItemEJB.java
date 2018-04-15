package com.smartThings.haflete.services.local;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smartThings.haflete.dao.ItemDAO;
import com.smartThings.haflete.entity.Item;
import com.smartThings.haflete.entity.Seller;
import com.smartThings.haflete.entity.searchBeans.ItemSearchCriteria;
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
		String ext = dir.substring(dir.indexOf(".") + 1);
		
		try {
			Files.write(Paths.get(dir), data);
			waterMarkImage(dir, ext);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("GENERAL_ERROR");
		}
        item.setFrontImgUrl(findUrlFromDir(dir));
		dao.saveOrUpdate(item);
		
        return item;
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

	@Override
	public List<Item> findItems(ItemSearchCriteria searchCriteria, Seller loginSeller) throws BusinessException {
		
		if(loginSeller == null || loginSeller.getStore() == null || loginSeller.getStore().getId() == 0) {
			throw new BusinessException("relogin");
		}
		
		List<Item> items = dao.findItems(searchCriteria,  loginSeller.getStore().getId());
		return items == null ? new ArrayList<>() : items;
	}
	
	@Override
	public List<Item> findItems(ItemSearchCriteria searchCriteria) throws BusinessException {
		List<Item> items = dao.findItems(searchCriteria);
		return items == null ? new ArrayList<>() : items;
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
