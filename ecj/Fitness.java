//package ec.app.priority_inflation;
import ec.*;
import ec.simple.*;
import ec.vector.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Fitness extends Problem implements SimpleProblemForm
    {
    public void evaluate(final EvolutionState state,
        final Individual ind,
        final int subpopulation,
        final int threadnum){

	if(ind.evaluated) return;

	if(!(ind instanceof IntegerVectorIndividual)){
		state.output.fatal("Error: 'ind' not instance of IntegerVectorIndividual",null);
	}

	IntegerVectorIndividual candidate = (IntegerVectorIndividual)ind;

	int numDevs = candidate.genome[0];
	int numTesters = candidate.genome[1];
	int numDefects = candidate.genome[2];

	String output = null;
	try{
		Process process = Runtime.getRuntime().exec("./fitness_script.bsh " + numDevs + " " + numTesters + " " + numDefects);
        	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        	String line;
		while((line=reader.readLine()) != null){
			if(output != null){
				reader.close();
				state.output.fatal("Script has output more than one line.\n" +
				"This breaks the contract. The evaluation script must return a single Dpuble value. Value Returned:\n" +
			 	output + "\n" + line, null);
			}
			output = line;
		}
		reader.close();
	} catch(IOException e){
		state.output.fatal("IOException thrown when running the script:" + e.getMessage(), null);
	}
	Double fitness = null;
	try{
		fitness = Double.parseDouble(output);
	} catch(NullPointerException e){
		state.output.fatal("Script produced no output\nException Information:\n"+e.getMessage(), null);
	} catch(NumberFormatException e){
		state.output.fatal("Script  produced output '" +output+"'. Not parsable\nException Information:\n" + e.getMessage(), null);
	}	
	((SimpleFitness)candidate.fitness).setFitness(state, fitness, false); //TODO: Last argument here is true if the fitness is ideal.
	candidate.evaluated = true;
	
   }

}
