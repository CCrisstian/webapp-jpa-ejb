package org.CCristian.apiservlet.webapp.headers.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;


@ApplicationScoped
public class ProducerResources {

    @Resource(lookup = "java:/MySqlDS")
    private DataSource ds;

    @Inject
    private Logger log;

    /*Devuelve la conexión a la BaseDeDatos*/
    @Produces
    @RequestScoped
    @MySQLConn
    private Connection beanConnection() throws NamingException, SQLException {
        return ds.getConnection();
    }

    @PersistenceUnit(name = "ejemploJpa")
    private EntityManagerFactory emf;

    /*Cierra la conexión a la BaseDeDatos*/
    public void close(@Disposes @MySQLConn Connection connection) throws SQLException {
        connection.close();
        log.info("Cerrando la conexión a la BD MySQL");   /*Para visualizarlo en la consola de 'Tomcat'*/
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @RequestScoped
    private EntityManager beanEntityManager(){
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager entityManager){
        if(entityManager.isOpen()){
            entityManager.close();
            log.info("Cerrando la conexión del EntityManager!");   /*Para visualizarlo en la consola de 'Tomcat'*/
        }
    }
}
