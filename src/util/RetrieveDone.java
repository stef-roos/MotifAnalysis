package util;

/**
 * exception for interruption motif retrieval if desired # of motifs are retrieved
 * @author stefanie
 *
 */
public class RetrieveDone extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public RetrieveDone(){
		super("Motifs retrieved!");
	}

}
