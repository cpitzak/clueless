/**
 *
 */
package com.jhu.clueless.pieces;

/**
 * @author ELO
 *
 */
public class CaseFile {

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((characterCard == null) ? 0 : characterCard.hashCode());
		result = prime * result
				+ ((roomCard == null) ? 0 : roomCard.hashCode());
		result = prime * result
				+ ((weaponCard == null) ? 0 : weaponCard.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaseFile other = (CaseFile) obj;
		if (characterCard == null) {
			if (other.characterCard != null)
				return false;
		} else if (!characterCard.equals(other.characterCard))
			return false;
		if (roomCard == null) {
			if (other.roomCard != null)
				return false;
		} else if (!roomCard.equals(other.roomCard))
			return false;
		if (weaponCard == null) {
			if (other.weaponCard != null)
				return false;
		} else if (!weaponCard.equals(other.weaponCard))
			return false;
		return true;
	}

	/**
	 * Murder character card.
	 */
	private CharacterCard characterCard;

	/**
	 * Murder room card.
	 */
	private RoomCard roomCard;

	/**
	 * Murder weapon card.
	 */
	private WeaponCard weaponCard;

	/**
	 * Default constructor of CaseFile.
	 */
	public CaseFile() {
	}

	/**
	 * Constructor of CaseFile.
	 * @param characterCard Murder character card
	 * @param roomCard Murder room card
	 * @param weaponCard Murder weapon card
	 */
	public CaseFile(CharacterCard characterCard, RoomCard roomCard, WeaponCard weaponCard) {
		this.setCharacterCard(characterCard);
		this.setRoomCard(roomCard);
		this.setWeaponCard(weaponCard);
	}

	/**
	 * @return the weaponCard
	 */
	public WeaponCard getWeaponCard() {
		return weaponCard;
	}

	/**
	 * @param weaponCard the weaponCard to set
	 */
	public void setWeaponCard(WeaponCard weaponCard) {
		this.weaponCard = weaponCard;
	}

	/**
	 * @return the roomCard
	 */
	public RoomCard getRoomCard() {
		return roomCard;
	}

	/**
	 * @param roomCard the roomCard to set
	 */
	public void setRoomCard(RoomCard roomCard) {
		this.roomCard = roomCard;
	}

	/**
	 * @return the characterCard
	 */
	public CharacterCard getCharacterCard() {
		return characterCard;
	}

	/**
	 * @param characterCard the characterCard to set
	 */
	public void setCharacterCard(CharacterCard characterCard) {
		this.characterCard = characterCard;
	}
}
