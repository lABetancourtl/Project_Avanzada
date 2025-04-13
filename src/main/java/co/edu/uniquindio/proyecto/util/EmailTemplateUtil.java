package co.edu.uniquindio.proyecto.util;

public class EmailTemplateUtil {

    public static String generarTemplateCodigoValidacion(String nombreUsuario, String codigoActivacion) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;">
                        <h2 style="color: #4CAF50; text-align: center;">Verificación de cuenta</h2>
                        <p>¡Hola <strong>%s</strong>!</p>
                        <p>Tu código de verificación es:</p>
                        <h1 style="background-color: #eee; padding: 10px; border-radius: 4px; text-align: center;">%s</h1>
                        <p>Si no solicitaste este código, por favor ignora este correo.</p>
                        <p style="font-size: 12px; color: #888;">Este es un correo automático, por favor no respondas.</p>
                    </div>
                </body>
            </html>
            """.formatted(nombreUsuario, codigoActivacion);
    }

    public static String generarTemplateNuevoComentario(String nombreUsuario, String tituloReporte, String mensajeComentario) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;">
                        <h2 style="color: #2196F3; text-align: center;">Nuevo comentario en tu reporte</h2>
                        <p>Hola <strong>%s</strong>,</p>
                        <p>Se ha agregado un nuevo comentario a tu reporte: <strong>%s</strong>.</p>
                        <p>Mensaje:</p>
                        <blockquote style="background-color: #f9f9f9; padding: 10px; border-left: 5px solid #ccc;">
                            %s
                        </blockquote>
                        <p>Gracias por contribuir a la seguridad de la comunidad.</p>
                        <p style="font-size: 12px; color: #888;">Este es un correo automático, por favor no respondas.</p>
                    </div>
                </body>
            </html>
            """.formatted(nombreUsuario, tituloReporte, mensajeComentario);
    }
    public static String generarTemplateCambioEstado(String nombreUsuario, String tituloReporte, String nuevoEstado) {
        return """
        <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;">
                    <h2 style="color: #4CAF50; text-align: center;">Estado de tu reporte actualizado</h2>
                    <p>¡Hola <strong>%s</strong>!</p>
                    <p>Queremos informarte que el estado de tu reporte titulado:</p>
                    <h3 style="text-align: center; color: #333;">"%s"</h3>
                    <p>Ha sido actualizado al siguiente estado:</p>
                    <h1 style="background-color: #eee; padding: 10px; border-radius: 4px; text-align: center;">%s</h1>
                    <p>Puedes ingresar a la plataforma para consultar los detalles.</p>
                    <p style="font-size: 12px; color: #888;">Este es un correo automático, por favor no respondas.</p>
                </div>
            </body>
        </html>
        """.formatted(nombreUsuario, tituloReporte, nuevoEstado);
    }
}
