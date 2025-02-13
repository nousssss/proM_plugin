/**
 * 
 */
package org.processmining.plugins.petrinet.manifestreplayresult;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TObjectIntMap;

import org.deckfour.xes.classification.XEventClass;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XLog;
import org.processmining.models.graphbased.directed.petrinet.PetrinetGraph;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.semantics.petrinet.Marking;
import org.processmining.plugins.petrinet.manifestreplayer.TransClass2PatternMap;
import org.processmining.plugins.petrinet.manifestreplayer.transclassifier.TransClass;

/**
 * @author aadrians May 17, 2012
 * 
 */
public class ManifestEvClassPattern extends Manifest {
	private TransClass2PatternMap transClass2PatternMap;

	public ManifestEvClassPattern(PetrinetGraph net, Marking initMarking, Marking[] finalMarkings, XLog log,
			Transition[] transArr, TObjectIntMap<Transition> trans2idx, int[] casePtr, boolean[] caseReliability, int[] info,
			TransClass2PatternMap transClass2PatternMap, TIntIntMap index2FitnessStats, int[] manifest2PatternID,
			double[] fitnessStats) {
		super(net, initMarking, finalMarkings, log, transArr, trans2idx, casePtr, caseReliability, info, index2FitnessStats,
				manifest2PatternID, fitnessStats);
		this.transClass2PatternMap = transClass2PatternMap;
	}
	
	/**
	 * @return the transClass2PatternMap
	 */
	public TransClass2PatternMap getTransClass2PatternMap() {
		return transClass2PatternMap;
	}

	public XEventClass[] getEventClassesOfPattern(short patternID) {
		return this.transClass2PatternMap.decodePatternID(patternID);
	}

	public XEventClassifier getEvClassifier(){
		return transClass2PatternMap.getEvClassifier();
	};

	public TransClass getTransClassOf(Transition t){
		return transClass2PatternMap.getTransClassOf(t);
	};
}
