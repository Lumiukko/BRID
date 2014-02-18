package net.brainscorch.BRID;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;


public class ImageMap implements Serializable {
	
	Map<String, ImageData> imageMap;
	
	public ImageMap() {
		this.imageMap = new HashMap<>();
	}
	
	public void addImage(ImageData imageData) {
		imageMap.put(imageData.getHashString(), imageData);
	}
	
	public ImageData getImageDataByHash(String hashString) {
		return imageMap.get(hashString);
	}
	
	public List<String> getImageHashList() {
		List<String> fileList = new ArrayList();
		Iterator iter = imageMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ImageData> entry = (Entry<String, ImageData>) iter.next();
			fileList.add(entry.getValue().getHashString());
		}
		return fileList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Found entries total: ");
		sb.append(imageMap.size());
		sb.append("\n");
		Iterator iter = imageMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ImageData> entry = (Entry<String, ImageData>) iter.next();
			sb.append(entry.getValue().toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public List<ImageData> searchImageData(String keyword) {
		List<ImageData> iDataList = new ArrayList();
		Iterator iter = imageMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ImageData> entry = (Entry<String, ImageData>) iter.next();
			if (entry.getValue().getDescription().contains(keyword))
				iDataList.add(entry.getValue());
			if (entry.getValue().getOriginalName().contains(keyword))
				iDataList.add(entry.getValue());
		}
		return iDataList;
	}
	
	public List<ImageData> getImageDataList() {
		List<ImageData> iDataList = new ArrayList();
		Iterator iter = imageMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, ImageData> entry = (Entry<String, ImageData>) iter.next();
			iDataList.add(entry.getValue());
		}
		return iDataList;
	}
}
