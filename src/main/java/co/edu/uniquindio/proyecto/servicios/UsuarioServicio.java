package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface UsuarioServicio {


    void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception;
    void editar(String id, EditarUsuarioDTO editarUsuarioDTO) throws Exception;
    void eliminar(String id) throws Exception;
    UsuarioDTO obtener(String id) throws Exception;
    List<InfoReporteDTO> obtenerReportesUsuario(String id);
    List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina);
    void enviarCodigoVerificacion(EnviarCodigoDTO enviarCoditoDTO) throws Exception;
    void cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception;
    void activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception;
}
