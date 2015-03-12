package com.jhu.clueless.pieces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ELO
 *
 */
public enum WeaponEnum {

	/**
	 * Candlestick.
	 */
	CANDLESTICK("Candlestick"),
	/**
	 * Knife.
	 */
	KNIFE("Knife"),
	/**
	 * Lead Pipe.
	 */
	LEADPIPE("Lead Pipe"),
	/**
	 * Revolver.
	 */
	REVOLVER("Revolver"),
	/**
	 * Rope.
	 */
	ROPE("Rope"),
	/**
	 * Wrench.
	 */
	WRENCH("Wrench");

	private static final List<String> WEAPON_NAMES;
	static {
		WEAPON_NAMES = new ArrayList<String>();
		for (WeaponEnum weaponEnum : WeaponEnum.values()) {
			WEAPON_NAMES.add(weaponEnum.getText());
		}
	}

	private String weapon;

	private WeaponEnum(String weapon) {
		this.weapon = weapon;
	}


	/**
	 *
	 * @return the Weapon.
	 */
	public String getText() {
		return weapon;
	}

	/**
	 * @return a list of weapon names
	 */
	public static List<String> valuesString() {
		return WEAPON_NAMES;
	}
}
