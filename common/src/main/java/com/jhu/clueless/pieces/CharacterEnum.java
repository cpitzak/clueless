package com.jhu.clueless.pieces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELO
 *
 */
public enum CharacterEnum {

	/**
	 * Miss Scarlet.
	 */
	SCARLET("Miss Scarlet"),
	/**
	 * Colonel Mustard.
	 */
	MUSTARD("Colonel Mustard"),
	/**
	 * Mrs. White.
	 */
	WHITE("Mrs. White"),
	/**
	 * Mr. Green.
	 */
	GREEN("Mr. Green"),
	/**
	 * Mrs. Peacock.
	 */
	PEACOCK("Mrs. Peacock"),
	/**
	 * Professor Plum.
	 */
	PLUM("Professor Plum");


	private static final List<String> CHARACTER_NAMES;
	static {
		CHARACTER_NAMES = new ArrayList<String>();
		for (CharacterEnum characterEnum : CharacterEnum.values()) {
			CHARACTER_NAMES.add(characterEnum.getText());
		}
	}

	private String character;

	private CharacterEnum(String character) {
		this.character = character;
	}

	/**
	 *
	 * @return the Character.
	 */
	public String getText() {
		return character;
	}

	/**
	 * @return a list of character names
	 */
	public static List<String> valuesString() {
		return CHARACTER_NAMES;
	}

}
