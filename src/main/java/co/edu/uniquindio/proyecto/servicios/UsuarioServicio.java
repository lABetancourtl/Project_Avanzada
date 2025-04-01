package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface UsuarioServicio {


    void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception;
    void editar(EditarUsuarioDTO editarUsuarioDTO) throws Exception;
    void eliminar(String id) throws Exception;
    UsuarioDTO obtener(String id) throws Exception;
    void enviarCodigoVerificacion(String email) throws Exception;
    void cambiarPassword(String email, CambiarPasswordDTO cambiarPasswordDTO) throws Exception;
    void activarCuenta(String email, ActivarCuentaDTO activarCuentaDTO) throws Exception;
    List<InfoReporteDTO> obtenerReportesUsuario(String id);

}
