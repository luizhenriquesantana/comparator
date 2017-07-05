package com.waes.comparator.enums;

/**
 * 
 * Enum with the possible sides.
 *
 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
 * @version $Revision: 1.1 $
 */
public enum Side {
	LEFT, RIGHT;
	
	@Override
	public String toString() {
		switch (this) {
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		}
		throw new Error("An error occurred while trying to get the correct side.");
	}
}
