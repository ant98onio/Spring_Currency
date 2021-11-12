package node.service;

import java.util.List;

import node.model.PairDto;
import node.model.RuoloDto;

public interface RuoloService {
	
	public List<RuoloDto> getAllRuoli();

	public String chiamaServizioEsterno() throws Exception;

	public List<PairDto> convertiLoScaricoDatiInJava() throws Exception;

	public List<PairDto> convertiLoScaricoDatiInJavaESalvaloNelDB() throws Exception;

	public List<PairDto> selectPairs(); 
	
}
