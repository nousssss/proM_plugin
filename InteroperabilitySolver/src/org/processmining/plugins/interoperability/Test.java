package org.processmining.plugins.interoperability;

import org.deckfour.uitopia.api.event.TaskListener.InteractionResult;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

@Plugin
(
	name = "Dialog test", 
	parameterLabels = {},
    returnLabels = {"sth"}, 
    returnTypes = {Object.class},
    userAccessible = true
 )

public class Test {
	private final static String PLUGIN = "";
	@UITopiaVariant
	  (
	     affiliation = "CDTA", 
	     author = "Bachiri Inas", 
	     email = "ji_bachiri@esi.dz"
	  )
	@PluginVariant 
	  (
		 variantLabel = "Test dialog", 
		 requiredParameterLabels = {}
	  )
	
	public static void dialog(UIPluginContext context) {
		//SolverDialog dialog = new SolverDialog();
		SolverDialog stuff = new SolverDialog(PLUGIN);
		InteractionResult result = context.showWizard("Interoperability Solver", true, true, stuff);
		if (result != InteractionResult.FINISHED) {
			System.out.println("coucou");
		}
		//return result;

}
}
