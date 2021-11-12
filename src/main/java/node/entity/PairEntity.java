package node.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PAIR")
public class PairEntity {

	@Id
    @Column(name="pair_value")
    private String pairValue;

	public String getPairValue() {
		return pairValue;
	}

	public void setPairValue(String pairValue) {
		this.pairValue = pairValue;
	}
     
}
