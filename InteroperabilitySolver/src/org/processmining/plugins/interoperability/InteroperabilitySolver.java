package org.processmining.plugins.interoperability;


import java.util.Collection;

import org.deckfour.uitopia.api.event.TaskListener.InteractionResult;
import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.interoperability.LabelledPetrinet;
import org.processmining.plugins.petrinet.replayresult.PNRepResult;

/**
 * Interoperability solving plug-in that annotates and repaires a process model after
 *   checking it for conformance with an event log.
 * 
 * @author nousssss
 * 
 */
@Plugin
(
	name = "Interoperability Solver", 
	parameterLabels = {"Process Model" , "Event Log"},
    returnLabels = { "Annotated Repaired Model" }, 
    returnTypes = { LabelledPetrinet.class },
    userAccessible = true
 )

public class InteroperabilitySolver {
	
	
	/**
	 * Variant 1 : The process model and the event are provided.
	 * 
	 * @param context
	 *            The given plug-in context.
	 * @param log
	 *            The given event log.
	 * @param net
	 *            The given process model.
	 * @return an annotated repaired model.
	 * @throws Exception 
	 *            
	 */
	@UITopiaVariant
	     (
			affiliation = "CDTA", 
			author = "Bachiri Ines", 
			email = "ji_bachiri@esi.dz"
	      )
	@PluginVariant
	      (
	        variantLabel = "Interoperability Solver, given args", 
	        requiredParameterLabels = { 0,1 }
	       )
	public LabelledPetrinet solveGiven(UIPluginContext context, Petrinet net, XLog log) throws Exception {
		Collection<InteroperabilitySolverConnection> connections;
		try {
			connections = context.getConnectionManager().getConnections(InteroperabilitySolverConnection.class, context, log);
			for (InteroperabilitySolverConnection connection : connections) {
				if (connection.getObjectWithRole(InteroperabilitySolverConnection.LOG).equals(log)
						&& connection.getObjectWithRole(InteroperabilitySolverConnection.NET).equals(net)) {
					return connection.getObjectWithRole(InteroperabilitySolverConnection.LABELLED);
				}
			}
		} catch (ConnectionCannotBeObtained e) {
		}
		LabelledPetrinet labelled = solver(context, net, log);
		context.addConnection(new InteroperabilitySolverConnection(log,net,labelled));
		return labelled;
	}

	/**
	 * Variant 2 : The process model and the event are to be imported.
	 * 
	 * @param context
	 *            The given plug-in context.
	 * @return an annotated repaired model.           
	 * @throws Exception 
	 */
	@UITopiaVariant
	     (
			affiliation = "CDTA", 
			author = "Bachiri Ines", 
			email = "ji_bachiri@esi.dz"
	      )
	@PluginVariant
	      (
	        variantLabel = "Interoperability Solver, imported args", 
	        requiredParameterLabels = {}
	       )
	public LabelledPetrinet solveImported(UIPluginContext context) throws Exception {
		SolverDialog dialog = new SolverDialog("");
		InteractionResult result = context.showWizard("Interoperability Solver", true, false, dialog);
		if (result != InteractionResult.NEXT) {
			return null;
		}
		XLog log = ImportLog.readLogFromFile();
		Object[] imported = ImportPetriNets.readPNFromFile(context);
		Petrinet net = (Petrinet) imported[0];
		return solveGiven(context, net, log);
	}

	/*
	 * The actual work.
	 */
	
	private LabelledPetrinet solver(UIPluginContext context,Petrinet net, XLog log) throws Exception {
		   
		    // Alignement
	    PNRepResult aligned = Alignment.check(context, log, net);
	    
	        // Repair
	    Petrinet repairedNet = Repair.repair(context, log, net, aligned);
	    
	        // Annotation
	    LabelledPetrinet result = Annotation.annotate(context, repairedNet, log);
	    
		return result;
	}
}

