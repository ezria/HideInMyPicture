package Interface;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter{

	public String getDescription() {
		return "Image non compréssée";
	}
	public boolean accept(File fichier) {
	    if (fichier.isDirectory())
	        return true;
	    String extension = null;
	    if (fichier!=null&&fichier.getName().lastIndexOf('.')!=-1) extension = fichier.getName().substring(fichier.getName().lastIndexOf('.'), fichier.getName().length());
	    if (extension != null && (extension.equals(".bmp")||extension.equals(".gif")||extension.equals(".png")))
	    	return true;
	    else
	    	return false;
	}

}
