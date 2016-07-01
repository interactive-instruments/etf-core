package de.interactive_instruments.etf.model.item;

import java.util.UUID;

/**
 * The etf ID class is intended to provide identifiers in the ETF environment and
 * maps a String to an internal presentation and an UUID representation
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface EID extends Comparable {

	/**
	 * Returns the ID as String representation
	 *
	 * The returned id will always be equal the string representation of the object
	 * from which the ID has been created, see {@link EidFactory}.
	 *
	 * @return string representation
	 */
    String getId();

    /**
     * Returns the ID in UUID representation
	 * (which may be a generated UUID hash from the internal ID)
     *
     * @return UUID representation
     */
    UUID toUuid();

	/**
	 * Returns the internal representation of the ID
	 * which is used by the framework.
	 *
	 * Note: getInternalId() != getId()
	 *
	 * @return
	 */
	String getInternalId();
}
