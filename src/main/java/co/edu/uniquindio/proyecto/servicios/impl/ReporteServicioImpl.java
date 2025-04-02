package co.edu.uniquindio.proyecto.servicios.impl;

import co.edu.uniquindio.proyecto.dto.CrearReporteDTO;
import co.edu.uniquindio.proyecto.dto.EditarReporteDTO;
import co.edu.uniquindio.proyecto.dto.ReporteDTO;
import co.edu.uniquindio.proyecto.modelo.documents.Reporte;
import co.edu.uniquindio.proyecto.modelo.documents.Usuario;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoReporte;
import co.edu.uniquindio.proyecto.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.proyecto.repositorios.ReporteRepositorio;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.proyecto.servicios.ReporteServicio;
import co.edu.uniquindio.proyecto.mapper.ReporteMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReporteServicioImpl implements ReporteServicio {


    private final ReporteRepositorio reporteRepositorioRepo;
    private final UsuarioRepositorio usuarioRepo;
    private final ReporteMapper reporteMapper;

    @Override
    public void crear(CrearReporteDTO dto) throws Exception {
        // Verificamos que el usuario exista y esté activo
        Usuario usuario = usuarioRepo.findById(new ObjectId(dto.getClienteId()))
                .orElseThrow(() -> new Exception("El usuario no existe"));
        if (usuario.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new Exception("El usuario está inactivo o eliminado");
        }
        // Convertimos el DTO a documento
        Reporte reporte = reporteMapper.toDocument(dto);
        // Asignamos valores adicionales que no vienen en el DTO
        reporte.setEstadoActual(EstadoReporte.PENDIENTE);
        reporte.setFecha(LocalDateTime.now());
        reporte.setContadorImportante(0);
        // Guardamos el reporte
        reporteRepositorioRepo.save(reporte);
    }




    @Override
    public void editar(String id, EditarReporteDTO reporte) {

    }

    @Override
    public void eliminar(String id) {

    }

    @Override
    public ReporteDTO obtener(String id) {
        return null;
    }

    @Override
    public void marcarImportante(String id) {

    }

    @Override
    public void cambiarEstado(String id, String nuevoEstado) {

    }
}
