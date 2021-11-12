package node.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="valuta")
public class ValutaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
	
    @Column(name="pair1")
    private String pair1;
    
    @Column(name="pair2")
    private String pair2;
    
    @Column(name="last")
    private String last;

    @Column(name="data")
    private Date data;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPair1() {
		return pair1;
	}

	public void setPair1(String pair1) {
		this.pair1 = pair1;
	}

	public String getPair2() {
		return pair2;
	}

	public void setPair2(String pair2) {
		this.pair2 = pair2;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
