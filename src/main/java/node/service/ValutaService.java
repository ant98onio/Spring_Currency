package node.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import node.model.ValutaDto;


public interface ValutaService {
	
	public List<ValutaDto> getAllValute();

	public String chiamaServizioEsterno();

	public List<ValutaDto> chiamaServizioEsternoConvertiInJava() throws JsonMappingException, JsonProcessingException;

	public List<ValutaDto> convertiLoScaricoDatiInJavaESalvaloNelDB() throws JsonMappingException, JsonProcessingException;

	public List<ValutaDto> getValutaByCurrency(String pair2);

}
