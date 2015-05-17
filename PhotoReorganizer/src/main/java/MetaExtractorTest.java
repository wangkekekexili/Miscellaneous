import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;


public class MetaExtractorTest {

	public static void main(String[] args) throws ImageProcessingException, IOException {
		File imageFile = new File("images/103992931.jpg");
		Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
		for (Directory directory : metadata.getDirectories()) {
			System.out.println(directory.getName());
			for (Tag tag : directory.getTags()) {
				System.out.println(tag.getDescription());
			}
			System.out.println();
		}
	}

}
