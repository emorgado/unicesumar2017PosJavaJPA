package posjava.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Exemplo2 {

	 public static void main( String[] args ) {

	        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "posjavaPU" );
	        EntityManager em = emf.createEntityManager();

	        // --- INICIO DA TRANSAÇÃO
	        EntityTransaction tx1 = em.getTransaction();
	        tx1.begin();
	        
	        tx1.commit();
	        
	        // Criar 10 garagens
	        // Criar 05 projetos
	        // Criar 04 departamentos
	        // Criar 08 Empregados
	        
	        // Associar
	        // Atibuir uma garagem para cada empregado
	        // Adicionar cada empregado a um departamento
	        // Adicionar cada empregado a um projeto
	        
	        
	 }
}
