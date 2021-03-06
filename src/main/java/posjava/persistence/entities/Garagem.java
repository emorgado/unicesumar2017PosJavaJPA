package posjava.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Garagem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long      id;
    private int       numero;
    private String    localizacao;

    @OneToOne
    @JoinColumn( name = "EMP_ID" )
    private Empregado empregrado;

    public Garagem() {

        super();
    }

    public Garagem( int numero, String localizacao ) {

        super();
        this.numero = numero;
        this.localizacao = localizacao;
    }

    public long getId() {

        return id;
    }

    public void setId( long id ) {

        this.id = id;
    }

    public int getNumero() {

        return numero;
    }

    public void setNumero( int numero ) {

        this.numero = numero;
    }

    public String getLocalizacao() {

        return localizacao;
    }

    public void setLocalizacao( String localizacao ) {

        this.localizacao = localizacao;
    }

    public Empregado getEmpregrado() {

        return empregrado;
    }

    public void setEmpregrado( Empregado empregrado ) {

        this.empregrado = empregrado;
    }

    @Override
    public String toString() {

        return String.format( "G%3d - %15s", numero,  localizacao );
    }

}
