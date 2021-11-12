package node.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import node.model.PairDto;
import node.model.RuoloDto;
import node.service.RuoloService;

@RestController
@Slf4j
@RequestMapping("/ruoli")
public class RuoloController {


	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	RuoloService ruoloService;

	@GetMapping(value="/helloWorld")
	public ResponseEntity<String> gethelloWorld(){

		return ResponseEntity.ok().body("Hello World");
	}


	// JEE STYLE CON EJB
	// creare il metodo per ottenere TUTTI i gatti
	//@GET
	//@Produces(MediaType.APPLICATION_JSON) //formato di dato
	//@Path("allGatti") //variabile {}

	// SPRING
	/**
	 * Get list of roles
	 * @return list of Ruoli existing on DB
	 */
	@GetMapping("/getListRuoli")
	public ResponseEntity<List<RuoloDto>> getListRuoli() {
	//	log.info("getListRuoli - START");
		List<RuoloDto> response = ruoloService.getAllRuoli();
	//	log.info("getListRuoli - response of count : {}", response.size());


		/*return Optional
				.ofNullable(response)
				.map( list -> ResponseEntity.ok().body(list))  
				.orElseGet( () -> ResponseEntity.notFound().build() );
		 */
		if(response.isEmpty()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}

	// fai una get di un servizio esterno
	@GetMapping("/scaricaDaServizioEsterno")
	public ResponseEntity<String> scaricaDaServizioEsterno() {
		//???
		// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
		try {
			String resultRequest = ruoloService.chiamaServizioEsterno();
			return ResponseEntity.ok().body(resultRequest);
		} catch (Exception exc) {
			return ResponseEntity.internalServerError().body(exc.getMessage());
		}
	}

	// fai una get di un servizio esterno E trasformalo in java
	@GetMapping("/convertiLoScaricoDatiInJava")
	public ResponseEntity<List<PairDto>> convertiLoScaricoDatiInJava() {
		//???
		// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
		try {
			List<PairDto> resultRequest = ruoloService.convertiLoScaricoDatiInJava();
			return ResponseEntity.ok().body(resultRequest);
		} catch (Exception exc) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// scarica, converti e salva nel DB
	@GetMapping("/convertiLoScaricoDatiInJavaESalvaloNelDB")
	public ResponseEntity<List<PairDto>> convertiLoScaricoDatiInJavaESalvaloNelDB() {
		//???
		// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
		try {
			List<PairDto> resultRequest = ruoloService.convertiLoScaricoDatiInJavaESalvaloNelDB();
			return ResponseEntity.ok().body(resultRequest);
		} catch (Exception exc) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// seleziona i dati scaricati nel DB
		@GetMapping("/selectPairs")
		public ResponseEntity<List<PairDto>> selectPairs() {
			//???
			// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
			try {
				List<PairDto> resultRequest = ruoloService.selectPairs();
				return ResponseEntity.ok().body(resultRequest);
			} catch (Exception exc) {
				return ResponseEntity.internalServerError().build();
			}
		}
	
}
