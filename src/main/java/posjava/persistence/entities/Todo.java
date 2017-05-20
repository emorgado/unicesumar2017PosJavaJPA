package posjava.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long   id;
	//@Column( name = "nome_tarefa")
    private String descricao;
    private String resumo;

    public Todo() {

        super();
    }

    public Todo( Long id, String descricao, String resumo ) {

        super();
        this.id = id;
        this.descricao = descricao;
        this.resumo = resumo;
    }

    public Todo( String descricao, String resumo ) {

        super();
        this.descricao = descricao;
        this.resumo = resumo;
    }

    
    public Long getId() {

        return id;
    }

    public void setId( Long id ) {

        this.id = id;
    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao( String descricao ) {

        this.descricao = descricao;
    }

    public String getResumo() {

        return resumo;
    }

    public void setResumo( String resumo ) {

        this.resumo = resumo;
    }

    @Override
    public String toString() {

        return "Todo: {id=" + id + ", descricao=" + descricao + ", resumo=" + resumo + "}";
    }

}
