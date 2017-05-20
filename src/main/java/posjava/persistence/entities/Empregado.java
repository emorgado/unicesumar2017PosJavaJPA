package posjava.persistence.entities;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPREGADOS")
// @Table( name="EMPREGADOS", schema="RH" )
// @Table( name="EMPREGADOS", catalog="RH" )
public class Empregado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_ID")
	private long id;

	@Column(name = "EMP_NOME")
	private String nome;

	@Column(name = "SAL")
	private long salario;

	@Column(name = "COM")
	//@Basic( fetch=FetchType.LAZY )  // -- Orienta para não trazer o valo da coluna no campo até ser necessário, porem o provider não é obrigado a respeitar isso
	//@Basic( fetch=FetchType.EAGER ) // -- Obrigatoriamente traz o valor da coluna no campo 
	private String comentario;

	@ManyToOne
	@JoinColumn( name="DEPT_ID")
	private Departamento departamento;
	
	@OneToOne
	@JoinColumn( name="GRG_ID")
	private Garagem garagem;
	
	@ManyToMany
	@JoinTable( name="EMP_PROJ",
	            joinColumns=@JoinColumn( name="EMP_ID"),
				inverseJoinColumns=@JoinColumn( name="PROJ_ID")
			  )	
	private Collection<Projeto> projetos;
	
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

	public long getSalario() {
		return salario;
	}

	public void setSalario(long salario) {
		this.salario = salario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
