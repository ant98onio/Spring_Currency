package node.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuoloDto {
	@JsonProperty("RuoloResponse")
	
 
	private String id;

	private String descrizioneRuolo;
	
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}
}
