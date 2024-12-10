package org.CCristian.apiservlet.webapp.headers.services;

import jakarta.ejb.Local;
import org.CCristian.apiservlet.webapp.headers.models.entities.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Local
public interface UsuarioService {
    Optional<Usuario> login(String username, String password);

    List<Usuario> listar() throws SQLException;
    Optional<Usuario> porId(Long id);
    void  guardar (Usuario usuario);
    void eliminar (Long id);
}