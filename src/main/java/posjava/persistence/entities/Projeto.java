package posjava.persistence.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name="PROJETOS" )
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long      id;
	private String    nome;
	
	@ManyToMany( mappedBy="projetos" )
	private Collection<Empregado> empregados;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Empregado> getEmpregados() {
		return empregados;
	}

	public void setEmpregados(Collection<Empregado> empregados) {
		this.empregados = empregados;
	}
	
}
