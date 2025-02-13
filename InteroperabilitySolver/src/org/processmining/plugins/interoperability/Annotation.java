package org.processmining.plugins.interoperability;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.interoperability.LabelledPetrinet;
import org.processmining.models.interoperability.LabelledPetrinetFactory;
import org.processmining.models.interoperability.LabelledTransition;


@Plugin
(
	name = "Annotation Plugin", 
	parameterLabels = {"Petri net" , "Event Log"},
    returnLabels = { "Annotated petrinet" }, 
    returnTypes = { LabelledPetrinet.class },
    userAccessible = true
 )

public class Annotation {
	
	@UITopiaVariant
	  (
	     affiliation = "CDTA", 
	     author = "Bachiri Inas", 
	     email = "ji_bachiri@esi.dz"
	  )
	@PluginVariant 
	  (
		 variantLabel = "Annotation Plugin yay", 
		 requiredParameterLabels = {0,1}
	  )
	
	public static LabelledPetrinet annotate(UIPluginContext context, Petrinet net, XLog log) {
		    // Converting the provided petrinet object into a labelled petrinet object
		LabelledPetrinet labeledNet = LabelledPetrinetFactory.clonePetrinet(net);

		for (XTrace trace : log) 
	    {
			for (XEvent event : trace) 
			{
				for (LabelledTransition trans : labeledNet.getTransitions()) 
				{
					String id = event.getAttributes().get("concept:name").toString();
				    String name = trans.getLabel();
				    if (name.equals(id) && event.getAttributes().containsKey("RECEIVE_MESSAGE") ) 
				     {
				    	trans.setMsgName(event.getAttributes().get("RECEIVE_MESSAGE").toString());
				    	trans.setMsgType(event.getAttributes().get("KIND").toString());
				    	   
				    	 // printing cuz I don't have a visualizer yet
				    	System.out.println(name + " :");
				    	System.out.println("\t Message : " + trans.getMsgName());
				    	System.out.println("\t Message type : " + trans.getMsgType() );
				    	System.out.println("\n");
				   
				        break;
				     }
				    if (name.equals(id) && event.getAttributes().containsKey("SEND_MESSAGE") ) 
				     {
				    	trans.setMsgName(event.getAttributes().get("SEND_MESSAGE").toString());
				    	trans.setMsgType(event.getAttributes().get("KIND").toString());
				    	// printing cuz I don't have a visualizer yet
				    	System.out.println(name + " :");
				    	System.out.println("\t Message : " + trans.getMsgName());
				    	System.out.println("\t Message type : " + trans.getMsgType() );
				    	System.out.println("\n");
				   
				        break;
				     }
				    if (name.equals(id) && !event.getAttributes().containsKey("SEND_MESSAGE") && !event.getAttributes().containsKey("RECEIVE_MESSAGE")  )
				     {
				    	// printing cuz I don't have a visualizer yet
				    	System.out.println(name + " :");
				    	System.out.println("\t Not annotated.\n");
		
				    }
			    }
		    }
	    }
		return labeledNet;

}
}
