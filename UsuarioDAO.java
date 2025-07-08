import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * @author Kevin
 */
public class UsuarioDAO {

    public boolean insertarUsuario(String nombre, String AP, String AM, String Dir, String Corr, String Tel) {
        String sql = "INSERT INTO alumnos (nombre, apellido_paterno, apellido_materno, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, AP);
            pstmt.setString(3, AM);
            pstmt.setString(4, Dir);
            pstmt.setString(5, Corr);
            pstmt.setString(6, Tel);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel obtenerUsuarioModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("apellido_paterno");
        model.addColumn("apellido_materno");
        model.addColumn("direccion");
        model.addColumn("correo");
        model.addColumn("telefono");

        String sql = "SELECT * FROM alumnos";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido_paterno"),
                    rs.getString("apellido_materno"),
                    rs.getString("direccion"),
                    rs.getString("correo"),
                    rs.getString("telefono")
                };
                model.addRow(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return model;
    }

    // ✅ MÉTODO PARA ELIMINAR USUARIO POR ID
    public boolean eliminarUsuarioPorId(int id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
