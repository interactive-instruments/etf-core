package de.interactive_instruments.etf.model.item;

import de.interactive_instruments.model.std.DefaultIdFactory;
import de.interactive_instruments.model.std.IdFactory;

import java.util.UUID;

/**
 * Interface for a factory that creates etf identifier objects.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface EidFactory {

    /**
     * Creates an EID with an internal random ID
     *
     * @return new EID
     */
    EID createRandomId();

	/**
     * The Factory ensures that:
     *
     * str equals createFromStrToUUID(str).getId();
     *
     * @param str
     * @return
     */
    EID createAndPreserveStr(String str);

	/**
     * The Factory ensures that:
     *
     * uuidStr equals createUUID(uuidStr).toUUID().toString()
	 * or that
	 * UUID.nameUUIDFromBytes(uuidStr).toString() equals
	 * createUUID(uuidStr).toUUID().toString()
     *
     * @param uuidStr
     * @return
     */
    EID createUUID(String uuidStr);

	/**
     * The Factory ensures that:
     *
     * uuid equals createAndPreserveUUID(uuid).toUUID();
     *
     * @param uuid
     * @return
     */
    EID createAndPreserveUUID(UUID uuid);

	static EidFactory getDefault() {
		return EidFactoryLoader.instance();
	}

}
