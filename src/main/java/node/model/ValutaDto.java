package node.model;

import java.util.Date;

public class ValutaDto {

    public Long id;
	
    public String pair1;
    
    public String pair2;
    
    public String last;
    
    public Date data;

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

	public void setData(Date expiry) {
		this.data = expiry;
	}
    
}
