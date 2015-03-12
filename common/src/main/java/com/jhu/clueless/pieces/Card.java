/**
 *
 */
package com.jhu.clueless.pieces;


/**
 * @author ELO
 *
 */
public abstract class Card {

	private String cardName;

	/**
	 * Default constructor of Card.
	 */
	public Card() {
	}

	/**
	 * Constructor of Card.
	 * @param cardName Name of the card
	 */
	public Card(String cardName) {
		this.setCardName(cardName);
	}

	/**
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * @param cardName the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cardName == null) ? 0 : cardName.hashCode());
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
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		return true;
	}
}
