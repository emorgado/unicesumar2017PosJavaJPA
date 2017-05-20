package posjava.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.ForeignKey;
import javax.persistence.Persistence;
import javax.persistence.Query;

import posjava.persistence.entities.Departamento;
import posjava.persistence.entities.Empregado;
import posjava.persistence.entities.Garagem;
import posjava.persistence.entities.Projeto;
import posjava.persistence.entities.Todo;

public class Exemplo2 {

    public static List< String > nomesEmpregados = Arrays.asList( "Julio", "Roberto", "Cervantes", "Genoveva", "Geneci", "Geralda", "Bianca", "Bino" );
    public static List< String > nomesProjetos = Arrays.asList( "Novas aguas", "Salve os indios", "Prenda os Corruptos", "Novas tecnologias", "Treinamentos" );
    public static List< String > nomesDepartamentos = Arrays.asList( "RH", "Produção", "TI", "Limpeza", "Estagio" );
    
    public static void main( String[] args ) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "posjavaPU" );
        EntityManager em = emf.createEntityManager();

        // Criar 10 garagens
        criaGaragens( em );

        EntityTransaction txCreate = em.getTransaction();
        txCreate.begin();

        // Criar 08 Empregados
        nomesEmpregados.forEach( nome -> {
            Empregado emp = new Empregado( nome );
            em.persist( emp );
        } );

        // Criar 05 projetos
        nomesProjetos.forEach( nome -> {
            Projeto proj = new Projeto( nome );
            em.persist( proj );
        } );

        // Criar 04 departamentos
        nomesDepartamentos.forEach( nome -> {
            Departamento dept = new Departamento( nome );
            em.persist( dept );
        } );
        txCreate.commit();

        Query buscaProjetos = em.createQuery( "SELECT p FROM Projeto p" );
        Collection<Projeto> projetos = buscaProjetos.getResultList();
        
        Query buscaEmpregado = em.createQuery( "from Empregado e" );
        List< Empregado > empregados = buscaEmpregado.getResultList();
        
        /*/
        Random r = new Random();
        EntityTransaction txAssocia = em.getTransaction();
        txAssocia.begin();
        projetos.forEach( p -> {
            
            //p.addEmpregado( empregados.stream().filter( e -> e.getNome().equals( nomesEmpregados.get( r.nextInt( nomesEmpregados.size() ) ) ) ).findFirst().get() );
            String nome = nomesEmpregados.get( r.nextInt( nomesEmpregados.size() ) );
            Empregado e1 = getEmpregadoByNome( em, nome );
            p.addEmpregado( e1 );
            
            Empregado e2 = getEmpregadoByNome( em, nome );
            if( e1.getId() != e2.getId() ){
                p.addEmpregado( e2 );
            }
            em.persist( p );
        } );
        txAssocia.commit();
        //*/
         
        // Associar
        fazAssociacoes( em );
        
        
        /*/  Exemplo de projection ...
         Query buscaNomes = em.createQuery( "SELECT f.nome, f.id FROM Empregado f" );
         List<String> nomes2 = buscaNomes.getResultList();
         //System.out.println( "nomes de empregados: " + buscaNomes.getResultList() );
         nomes2.forEach( e -> {
         System.out.println( " : " + e );
         });
        //*/
         
        /*/
        List< Empregado > empregadosFinal = buscaEmpregado.getResultList();
        empregadosFinal.forEach( e -> {
            String saida = String.format( "Empregado: %15s do departamento: %10s, usa a garagem: %10s e particiapa dos projetos: %s ", e.getNome(), e.getDepartamento().getNome(), e.getGaragem(), e.getProjetos()  ) ;
            System.out.println( saida );
        } );
        //*/
        
        /*/
        List< Projeto > projetosFinal = buscaProjetos.getResultList();
        projetosFinal.forEach( e -> {
            String saida = String.format( "Projeto: %15s , empregados: %s", e.getNome(), e.getEmpregados() ) ;
            System.out.println( saida );
        } );
        //*/
        
        // Buscar todos os empregados que estão no departamento de limpeza
        System.out.println("EMPREGADOS DA LIMPEZA");
        // @formatter:off
        String queryStringLimpeza = "FROM Empregado e "
                                   +" WHERE e.departamento.nome = 'Limpeza'";
        // @formatter:on
        Query buscaEmpregadosLimpeza = em.createQuery( queryStringLimpeza );
        List< Empregado > empregadosDaLimpeza = buscaEmpregadosLimpeza.getResultList();
        empregadosDaLimpeza.forEach( System.out::println );
        
        // Buscar Todos os funcionários que tem a garagem no pátio
        System.out.println("EMPREGADOS COM GARAGEM NO PATIO");
     // @formatter:off
        String queryStringGaragemNoPatio = "FROM Empregado e "
                                          +" WHERE e.garagem.localizacao = 'patio'";
        // @formatter:on
        Query buscaEmpregadosComGaragemNoPatio = em.createQuery( queryStringGaragemNoPatio );
        List< Empregado > empregadosComGaragemNoPatio = buscaEmpregadosLimpeza.getResultList();
        empregadosComGaragemNoPatio.forEach( System.out::println );

        // Buscar Todos os funcionários da limpeza que participam do projeto Prenda os corruptos
        System.out.println("EMPREGADOS DA LIMPEZA DO PROJETO PRENDA OS CORRUPTOS");
        Query buscaEmpregadosLimpezaDoProjetoPrendaCorruptos = em.createQuery( "from Empregado e" );
        List< Empregado > empregadosLimpezaDoProjetoPrendaCorruptos = buscaEmpregadosLimpezaDoProjetoPrendaCorruptos.getResultList();
        //empregadosLimpezaDoProjetoPrendaCorruptos.forEach( System.out::println );
    }

    private static Query fazAssociacoes( EntityManager em ) {

        // Atibuir uma garagem para cada empregado
        // Adicionar cada empregado a um departamento
        EntityTransaction txAssocia = em.getTransaction();
        txAssocia.begin();
                
        Query buscaEmpregado = em.createQuery( "from Empregado e" );
        List< Empregado > empregados = buscaEmpregado.getResultList();
        
        Random r = new Random();
        
        for ( int i = 0; i < empregados.size(); i++ ) {
            Empregado e = empregados.get( i );
            Garagem g = getGaragemByNumero( em, e.getId() );
            e.setGaragem( g );
            
            Departamento d = getDepartamentoByNome( em, nomesDepartamentos.get( r.nextInt(nomesDepartamentos.size() ) ) );
            e.setDepartamento( d );
            
            //  Adicionar cada empregado a um projeto
            Projeto p = getProjetoByNome( em, nomesProjetos.get( r.nextInt( nomesProjetos.size() ) ) );
            Projeto p2 = getProjetoByNome( em, nomesProjetos.get( r.nextInt( nomesProjetos.size() ) ) );
//            List<Projeto> projetos = new ArrayList<>();
//            projetos.add( p );
            e.addProjeto( p );
            if( p.getId() != p2.getId() ){
//                projetos.add( p2 );
                e.addProjeto( p2 );
            }
//            e.setProjetos( projetos );
            
            
                       
            System.out.println( String.format( "Associando garagem: %d para %s, do departamento: %s", g.getNumero(), e.getNome(), d.getNome() ) );
            em.persist( e );
        }

        txAssocia.commit();
        return buscaEmpregado;
    }

    private static Empregado getEmpregadoByNome( EntityManager em, String nome ) {

        String queryString = "SELECT e FROM Empregado e where e.nome = '" + nome +"'";
        Query busca = em.createQuery( queryString );
        Empregado d1 = (Empregado) busca.getSingleResult();
        System.out.println( "Empregado " + d1 );
        return d1;

    }
    
    private static Departamento getDepartamentoByNome( EntityManager em, String nome ) {

        String queryString = "SELECT d FROM Departamento d where d.nome = '" + nome +"'";
        Query busca = em.createQuery( queryString );
        Departamento d1 = (Departamento) busca.getSingleResult();
        System.out.println( "Departamento " + d1 );
        return d1;

    }
    
    private static Projeto getProjetoByNome( EntityManager em, String nome ) {

        String queryString = "SELECT d FROM Projeto d where d.nome = '" + nome +"'";
        Query busca = em.createQuery( queryString );
        Projeto d1 = (Projeto) busca.getSingleResult();
        System.out.println( "Projeto " + d1 );
        return d1;

    }

    private static Garagem getGaragemByNumero( EntityManager em, long numero ) {

        String queryString = "SELECT g FROM Garagem g where g.numero = " + numero;
        Query buscaG1 = em.createQuery( queryString );
        Garagem g1 = (Garagem) buscaG1.getSingleResult();
        System.out.println( "Garagem " + g1 );
        return g1;
    }

    private static void criaGaragens( EntityManager em ) {

        // --- INICIO DA TRANSAÇÃO
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        Garagem garagem1 = new Garagem( 1, "patio" );
        em.persist( garagem1 );

        Garagem garagem2 = new Garagem( 2, "patio" );
        em.persist( garagem2 );

        Garagem garagem3 = new Garagem( 3, "patio" );
        em.persist( garagem3 );

        Garagem garagem4 = new Garagem( 4, "fundos" );
        em.persist( garagem4 );

        Garagem garagem5 = new Garagem( 5, "fundos" );
        em.persist( garagem5 );

        Garagem garagem6 = new Garagem( 6, "fundos" );
        em.persist( garagem6 );

        Garagem garagem7 = new Garagem( 7, "central" );
        em.persist( garagem7 );

        Garagem garagem8 = new Garagem( 8, "central" );
        em.persist( garagem8 );

        Garagem garagem9 = new Garagem( 9, "central" );
        em.persist( garagem9 );

        Garagem garagem10 = new Garagem( 10, "central" );
        em.persist( garagem10 );
        tx1.commit();

        Query buscaGaragens = em.createQuery( "from Garagem g" );
        List< Garagem > garagens = buscaGaragens.getResultList();
        garagens.forEach( System.out::println );
    }
}
// emerson.morgado@gmail.com

