package node.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import node.entity.PairEntity;
import node.entity.RuoloEntity;
import node.model.PairDto;
import node.model.RuoloDto;
import node.repository.PairRepository;
import node.repository.RuoliRepository;

@Service
public class RuoloServiceImpl implements RuoloService{
	
	@Autowired
	public RuoliRepository ruoliRepository;
	
	@Autowired
	public PairRepository pairRepository;
	
	public List<RuoloDto> getAllRuoli() {
		
		// JEE STYLE CON HIBERNATE E NAMEDQUERY
		//em.createNamedQuery(Padrone.NAMED_QUERY_ALL, Padrone.class)
		//.getResultList();
		
		// SPRING REPOSITORY
		List<RuoloEntity> listRuoloEntity = ruoliRepository.findAll();
		
		return listRuoloEntity.stream()
				.map(entity -> {
					RuoloDto dto = new RuoloDto();
					dto.setDescrizioneRuolo(entity.getDescrizioneRuolo());
					dto.setId(entity.getId());
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	public void saveRuolo(RuoloEntity ruolo) {
		ruoliRepository.save(ruolo);
	}

	@Override
	public String chiamaServizioEsterno() throws Exception {
		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
		//ResteasyClient client = new ResteasyClientBuilder().build();
		//client.target("https://cex.io/api/tickers/EUR");
		
		// avvia client per l'esecuzione di una chiamata REST (GET)
		ClientRequest request = new ClientRequest("https://cex.io/api/tickers/EUR");
		// avvia la chiamata REST
		ClientResponse<String> response = request.get(String.class);
		// prendimi il contenuto della risposta
		String responseString = response.getEntity();
		// ritorna il contenuto della risposta
		return responseString;
		
	}

	@Override
	public List<PairDto> convertiLoScaricoDatiInJava() throws Exception {
		String response = chiamaServizioEsterno();
		
		// main thread
		// si ma come facciamo a prendere solo l'elemento DATA
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response);
		
		// scorri lista e fammi vedere cosa hai dentro!!
		/* dataContent = jsonNode.get("data");
		 * for(String dataRow : dataContent) {
			System.out.println(dataRow);
		}*/
		
		final List<PairDto> listPairDto = new ArrayList<PairDto>();
		
		// ogni riga del json
		jsonNode.get("data").forEach(dataRow -> {

			String pairEstratto = dataRow.get("pair").asText();

			PairDto pairDto = new PairDto();
			pairDto.setPair(pairEstratto);

			listPairDto.add(pairDto);
		});
		
		return listPairDto;
	}

	@Override
	public List<PairDto> convertiLoScaricoDatiInJavaESalvaloNelDB() throws Exception {
		List<PairDto> result = convertiLoScaricoDatiInJava();
		
		// si ma come si salva sul db? 
		// convertire in entita
		List<PairEntity> entityResult = result.stream().map(
			// per ogni elemento del mio result, tu devi fare una funzione!
			pairDtoCheStoScorrendo -> {
				// questa è la funzione!!
				PairEntity pairEntity = new PairEntity();
				pairEntity.setPairValue(pairDtoCheStoScorrendo.getPair());
				return pairEntity;
			}
		).collect(Collectors.toList());
		
		// java7
		/*
		 * -- inizializza la variable entity
		 * -- foreach di resultDTO (map)
		 * creami un nuovo entity
		 * riempimi il nuovo entity
		 * -- assegna l'entita tramite listEntity.add() (map)
		 * 
		 */
		
		// salvare le entita!
		pairRepository.saveAll(entityResult);
		return result;
	}

	@Override
	public List<PairDto> selectPairs() {
		// scarica dati
		List<PairEntity> listEntities = pairRepository.findAll();
		
		// trasforma da entity a DTO
		List<PairDto> listPairDto = listEntities.stream().map(
			// scorro ogni elemento (pairEntity)
			pairEntity -> {
				// convert il pairEntity in DTO
				PairDto pairDto = new PairDto();
				pairDto.setPair(pairEntity.getPairValue());
				return pairDto;
			}
			// lo trasforma da stream a collection
		).collect(Collectors.toList());
		
		return listPairDto;
	}
	
}
