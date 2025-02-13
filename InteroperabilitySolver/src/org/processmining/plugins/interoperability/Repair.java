package org.processmining.plugins.interoperability;

import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.modelrepair.plugins.Uma_RepairModel_Subprocess_Plugin;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.plugins.petrinet.replayresult.PNRepResult;

@Plugin
(
	name = "nouss tests repair", 
	parameterLabels = {"Process Model" , "Event Log", "Replay result"},
    returnLabels = { "repaired net" }, 
    returnTypes = { Petrinet.class },
    userAccessible = true
 )

public class Repair {
	
	@UITopiaVariant
    (
		affiliation = "CDTA", 
		author = "Bachiri Ines", 
		email = "ji_bachiri@esi.dz"
     )
@PluginVariant
     (
       variantLabel = "nouss tests repair", 
       requiredParameterLabels = { 0,1,2 }
      )

	public static Petrinet repair(UIPluginContext context, XLog log, Petrinet net, PNRepResult aligned) {
	//Reparation
    Uma_RepairModel_Subprocess_Plugin repairPlugin = new Uma_RepairModel_Subprocess_Plugin();
    System.out.println("I'm about to do the repair");
    try {
       Object[] repRes = repairPlugin.repairModel(context, log, net,aligned) ;
       Petrinet repairedNet = (Petrinet) repRes[0];
       return repairedNet;
    }
    catch (ArrayIndexOutOfBoundsException e) {
    	System.out.println("what happened?");
 
    }
    System.out.println("Bonjour rby m3ak");
    return null;
    
	}
    
}
