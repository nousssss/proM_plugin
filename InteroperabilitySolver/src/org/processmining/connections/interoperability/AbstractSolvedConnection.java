package org.processmining.connections.interoperability;


import org.deckfour.xes.model.XLog;
import org.processmining.framework.connections.impl.AbstractConnection;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.interoperability.LabelledPetrinet;

/**
 * Connects an event log and a petrinet to an annotated repaired petrinet.
 * 
 * @author nousssss
 * 
 */

public abstract class AbstractSolvedConnection extends AbstractConnection {
	/**
	 * Label for the log end of the connection.
	 */
	public final static String LOG = "Log";
	/**
	 * Label for the petrinet end of the connection.
	 */
	public final static String NET = "Petrinet";
	/**
	 * Label for the annotated repaired end of the connection.
	 */
	public final static String LABELLED = "Repaired annotated petrinet";



	/**
	 * Creates a connection from an event log and a petrinet to an annotated petrinet, where the
	 * annotated petrinet is the repaired petrinet resulting from a replay of the log on the model.
	 * 
	 * @param log
	 *            The event log.
	 * @param net
	 *            The petrinet (process model).
	 * @param labelled
	 *            the repaired annotated petrinet
	 */
	public AbstractSolvedConnection(XLog log, Petrinet net, LabelledPetrinet labelled) {
		super("Annotated repaired model for log and petrinet");
		put(LOG, log);
		put(NET, net);
		put(LABELLED, labelled);
	}

}
