package posjava.persistence.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table( name = "DEPARTAMENTOS" )
public class Departamento {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long                    id;
    private String                  nome;

    @OneToMany( mappedBy = "departamento" )
    @OrderBy( "nome ASC" )
    private Collection< Empregado > empregados;

    public Departamento() {

        super();
        // TODO Auto-generated constructor stub
    }

    public Departamento( String nome ) {

        super();
        this.nome = nome;
    }

    public long getId() {

        return id;
    }

    public void setId( long id ) {

        this.id = id;
    }

    public String getNome() {

        return nome;
    }

    public void setNome( String nome ) {

        this.nome = nome;
    }

}
