package node.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import node.entity.ValutaEntity;
import node.model.ValutaDto;
import node.repository.ValutaRepository;

@Service
public class ValutaServiceImpl implements ValutaService {

	@Autowired
	public ValutaRepository valutaRepository;

	@Override
	public List<ValutaDto> getAllValute() {
		
		List<ValutaEntity> listValutaEntity = valutaRepository.findAll();
		
		return listValutaEntity.stream()
				.map(entity -> {
					ValutaDto dto = new ValutaDto();
					dto.setId(entity.getId());
					dto.setPair1(entity.getPair1());
					dto.setPair2(entity.getPair2());
					dto.setData(entity.getData());
					dto.setLast(entity.getLast());
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public String chiamaServizioEsterno() {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("https://cex.io/api/tickers/EUR");

		Response response = target.request(MediaType.APPLICATION_JSON).get();

		String value = response.readEntity(String.class);
		// System.out.println(value);
		response.close();

		return value;
	}

	@Override
	public List<ValutaDto> chiamaServizioEsternoConvertiInJava() throws JsonMappingException, JsonProcessingException {

		String data = chiamaServizioEsterno();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(data);

		final List<ValutaDto> lista = new ArrayList<>();

		jsonNode.get("data").forEach(dataRow -> {

			ValutaDto valutaDto = new ValutaDto();

			String timestamp = dataRow.get("timestamp").asText();
			String last = dataRow.get("last").asText();
			String pair = dataRow.get("pair").asText();
			
			
			long epoch = Long.parseLong( timestamp );
			Date expiry = new Date( epoch * 1000 );

			String[] pairDaSplittare = pair.split(":");
			String pair1 = pairDaSplittare[0];
			String pair2 = pairDaSplittare[1];

			valutaDto.setData(expiry);
			valutaDto.setPair2(pair1);
			valutaDto.setPair1(pair2);
			valutaDto.setLast(last);

			lista.add(valutaDto);
		});
		return lista;
	}

	@Override
	public List<ValutaDto> convertiLoScaricoDatiInJavaESalvaloNelDB() throws JsonMappingException, JsonProcessingException {
		
		List<ValutaDto> resultList = chiamaServizioEsternoConvertiInJava();

		List<ValutaEntity> entityResult = resultList.stream().map(
				// per ogni elemento del mio result, tu devi fare una funzione!
				valutaDto -> {
					// questa è la funzione!!
					ValutaEntity valutaEntity = new ValutaEntity();
					valutaEntity.setData(valutaDto.getData());
					valutaEntity.setPair1(valutaDto.getPair1());
					valutaEntity.setPair2(valutaDto.getPair2());
					valutaEntity.setLast(valutaDto.getLast());
					return valutaEntity;
				}).collect(Collectors.toList());
	
		valutaRepository.saveAll(entityResult);
		return resultList;
	}

	@Override
	public List<ValutaDto> getValutaByCurrency(String pair2) {
		
		List<ValutaEntity> listaValute = valutaRepository.findByPair2(pair2);

		return listaValute.stream().map(entity -> {
			ValutaDto dto = new ValutaDto();
			dto.setId(entity.getId());
			dto.setPair1(entity.getPair1());
			dto.setPair2(entity.getPair2());
			dto.setData(entity.getData());
			dto.setLast(entity.getLast());
			return dto;
		}).collect(Collectors.toList());
	}
}
