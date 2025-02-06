package de.willuhn.jameica.fibu.server;

import java.rmi.RemoteException;

import de.willuhn.datasource.db.AbstractDBObject;
import de.willuhn.jameica.fibu.rmi.Version;

/**
 * Tabelle f�r den Zugriff auf die Versionen.
 */
public class VersionImpl extends AbstractDBObject implements Version
{

	/**
   * Erzeugt eine neue Version.
   * @throws RemoteException
   */
	public VersionImpl() throws RemoteException {
		super();
	}

	@Override
	protected String getTableName() {
		return "version";
	}

	@Override
	public String getPrimaryAttribute() throws RemoteException {
		return "name";
	}

}
