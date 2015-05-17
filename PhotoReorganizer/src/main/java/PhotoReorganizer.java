import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;


public class PhotoReorganizer {

	Map<String, File> captionToImageFile;
	
	{
		captionToImageFile = new HashMap<>();
	}
	
	public static String getImageCaption(File imageFile) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
			for (Directory d : metadata.getDirectories()) {
				for (Tag tag : d.getTags()) {
					if (tag.getTagName().contains("Caption/Abstract")) {
						return tag.getDescription();
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	private void loadCaptions() {
		File folderFile = new File("images");
		File[] imageFiles = folderFile.listFiles();
		for (File imageFile : imageFiles) {
			String caption = getImageCaption(imageFile);
			if (caption != null) {
				this.captionToImageFile.put(caption, imageFile);
			}
		}
	}
	
	private void reorganize() {
		for (Entry<String, File> entry : this.captionToImageFile.entrySet()) {
			String caption = entry.getKey();
			File imageFile = entry.getValue();
			
			String firstChar = caption.substring(0, 1);
			File folder = new File("images/"+firstChar);
			if (folder.exists() == false) {
				folder.mkdir();
			}
			
			try {
				Files.move(imageFile.toPath(), 
						new File(folder.toPath()+"/"+imageFile.getName()).toPath(), 
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void doWork() {
		loadCaptions();
		reorganize();
	}
	
	public static void main(String[] args) {
		new PhotoReorganizer().doWork();
	}

}
