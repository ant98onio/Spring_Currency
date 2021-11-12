package node.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

import node.model.ValutaDto;
import node.service.ValutaService;

@RestController
@Slf4j
@RequestMapping("/Valute")
public class ValutaController {

	@Autowired
	ValutaService valutaService;
	
	@GetMapping("/getAllValute")
	public ResponseEntity<List<ValutaDto>> getAllValute() {

		List<ValutaDto> response = valutaService.getAllValute();

		if(!response.isEmpty()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}
	
	@GetMapping("/scaricaDaServizioEsterno")
	public ResponseEntity<String> scaricaDaServizioEsterno() {
		String resultRequest = valutaService.chiamaServizioEsterno();
		return ResponseEntity.ok().body(resultRequest);
	}
	
	@GetMapping("/scaricaDaServizioEsternoConvertiJava")
	public ResponseEntity<List<ValutaDto>> scaricaDaServizioEsternoConvertiJava() throws JsonMappingException, JsonProcessingException {
		List<ValutaDto> resultList = valutaService.chiamaServizioEsternoConvertiInJava();
		return ResponseEntity.ok().body(resultList);
	}
	
	@GetMapping("/scaricaDaServizioEsternoSalvaNelDB")
	public ResponseEntity<List<ValutaDto>> scaricaDaServizioEsternoSalvaNelDB() throws JsonMappingException, JsonProcessingException {
		List<ValutaDto> resultRequest = valutaService.convertiLoScaricoDatiInJavaESalvaloNelDB();
		return ResponseEntity.ok().body(resultRequest);
	}
	
	@GetMapping("/ValutaByCurrency")
	public ResponseEntity<List<ValutaDto>> getValutaByCurrency(@RequestParam String pair2) {
		
		List<ValutaDto> response = valutaService.getValutaByCurrency(pair2);
		
		if (response.isEmpty()) {
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.ok().body(response);
	}
}
