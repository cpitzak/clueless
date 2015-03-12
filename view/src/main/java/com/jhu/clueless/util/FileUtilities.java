package com.jhu.clueless.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines a File Utility class.
 *
 * @author Clint Pitzak
 *
 */
public class FileUtilities {

	private static Logger logger = LoggerFactory.getLogger(FileUtilities.class);

	/**
	 * Gets an image with a given name from the image folder in resources.
	 * Image to be loaded need to be in the png format.
	 *
	 * @param image the image name and extension to use
	 *
	 * @return the JLabel containing the image
	 */
	public static Icon getIcon(String image) {
		try {
			return new ImageIcon(FileUtilities.class.getResource("/images/" + image));
		} catch (NullPointerException e) {
			logger.error("cannot find image: " + image);
		}
		return null;
	}

}
