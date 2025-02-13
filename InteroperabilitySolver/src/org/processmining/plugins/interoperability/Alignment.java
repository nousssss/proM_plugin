package org.processmining.plugins.interoperability;

import org.deckfour.xes.classification.XEventClass;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.info.XLogInfoFactory;
import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.modelrepair.plugins.align.Uma_AlignForGlobalRepair_Plugin;
import org.processmining.models.graphbased.directed.petrinet.PetrinetGraph;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.plugins.connectionfactories.logpetrinet.TransEvClassMapping;
import org.processmining.plugins.petrinet.replayresult.PNRepResult;

@Plugin
(
	name = "nouss tests alignment", 
	parameterLabels = {"Process Model" , "Event Log"},
    returnLabels = { "Replay result" }, 
    returnTypes = { PNRepResult.class },
    userAccessible = true
 )

public class Alignment {
	
	public static TransEvClassMapping constructMapping(PetrinetGraph net, XLog log,
			XEventClass dummyEvClass, XEventClassifier eventClassifier) 
  {
		TransEvClassMapping mapping = new TransEvClassMapping(eventClassifier,dummyEvClass);
		XLogInfo summary = XLogInfoFactory.createLogInfo(log, eventClassifier);
		for (Transition t : net.getTransitions())
		   {
				//boolean mapped = false;
				for (XEventClass evClass : summary.getEventClasses().getClasses()) 
				 {
					String id = evClass.getId();
					String label = t.getLabel();
					id = id.substring(0, id.length()-1);
					if (label.equals(id)) 
					 {
					   mapping.put(t, evClass);
					 //  mapped = true;
					   break;
					 }
				  }
		   }
		return mapping;
   }
	
	@UITopiaVariant
    (
		affiliation = "CDTA", 
		author = "Bachiri Ines", 
		email = "ji_bachiri@esi.dz"
     )
@PluginVariant
     (
       variantLabel = "nouss tests alignment", 
       requiredParameterLabels = { 0,1 }
      )
	
	public static PNRepResult check(UIPluginContext context,XLog log, PetrinetGraph net) throws Exception {
				
		Uma_AlignForGlobalRepair_Plugin alignPlugin = new Uma_AlignForGlobalRepair_Plugin();
		return alignPlugin.getGlobalAlignment(context, log, net);
		
	}

}
	
