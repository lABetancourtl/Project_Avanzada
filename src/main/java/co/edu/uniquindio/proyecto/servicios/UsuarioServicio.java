package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface UsuarioServicio {

    // Registrar un nuevo usuario
    void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception;

    // Editar datos del usuario
    void editar(EditarUsuarioDTO editarUsuarioDTO) throws Exception;

    // Eliminar usuario (estado lógico)
    void eliminar(String id) throws Exception;

    // Obtener información de un usuario específico
    UsuarioDTO obtener(String id) throws Exception;

    void enviarCodigoVerificacion(String email) throws Exception;

    void cambiarPassword(String email, CambiarPasswordDTO cambiarPasswordDTO) throws Exception;

    void activarCuenta(String email, ActivarCuentaDTO activarCuentaDTO) throws Exception;


    // Listar usuarios con filtros y paginación
    List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina);

    // Login: autenticar usuario y retornar token
    TokenDTO login(LoginDTO loginDTO) throws Exception;

    // Activar la cuenta con código de validación

    // Verificar si un email ya está registrado
    boolean validarEmail(String email);

    void obtenerReportesUsuario(String id);
}
