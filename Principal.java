import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Principal extends JFrame {

    private JTextField TxtNombre, TxtAP, TxtAM, TxtDirec, TxtCorreo, TxtTel;
    private JButton BtnGuardar, BtnActualizar;
    private JTable tblUsuarios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Principal() {
        setTitle("Registro de alumnos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Registro de alumnos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(300, 10, 300, 30);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre(s):");
        lblNombre.setBounds(30, 50, 100, 20);
        add(lblNombre);

        TxtNombre = new JTextField();
        TxtNombre.setBounds(110, 50, 150, 20);
        add(TxtNombre);

        JLabel lblAP = new JLabel("A.P.:");
        lblAP.setBounds(280, 50, 40, 20);
        add(lblAP);

        TxtAP = new JTextField();
        TxtAP.setBounds(320, 50, 150, 20);
        add(TxtAP);

        JLabel lblAM = new JLabel("A.M.:");
        lblAM.setBounds(490, 50, 40, 20);
        add(lblAM);

        TxtAM = new JTextField();
        TxtAM.setBounds(530, 50, 150, 20);
        add(TxtAM);

        JLabel lblDirec = new JLabel("Direc.:");
        lblDirec.setBounds(30, 80, 100, 20);
        add(lblDirec);

        TxtDirec = new JTextField();
        TxtDirec.setBounds(110, 80, 570, 20);
        add(TxtDirec);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(30, 110, 100, 20);
        add(lblCorreo);

        TxtCorreo = new JTextField();
        TxtCorreo.setBounds(110, 110, 250, 20);
        add(TxtCorreo);

        JLabel lblTel = new JLabel("Teléf.:");
        lblTel.setBounds(390, 110, 100, 20);
        add(lblTel);

        TxtTel = new JTextField();
        TxtTel.setBounds(450, 110, 230, 20);
        add(TxtTel);

        tblUsuarios = new JTable();
        JScrollPane scroll = new JScrollPane(tblUsuarios);
        scroll.setBounds(30, 150, 720, 150);
        add(scroll);

        BtnGuardar = new JButton("Guardar");
        BtnGuardar.setBounds(200, 320, 100, 30);
        add(BtnGuardar);

        BtnActualizar = new JButton("Actualizar");
        BtnActualizar.setBounds(480, 320, 100, 30);
        add(BtnActualizar);

        // Asocia eventos con métodos
        BtnGuardar.addActionListener(e -> guardarAlumno());
        BtnActualizar.addActionListener(e -> actualizarTabla());

        actualizarTabla();

        setVisible(true);
    }

    private void guardarAlumno() {
        String nombre = TxtNombre.getText().trim();
        String ap = TxtAP.getText().trim();
        String am = TxtAM.getText().trim();
        String direc = TxtDirec.getText().trim();
        String correo = TxtCorreo.getText().trim();
        String tel = TxtTel.getText().trim();

        if (nombre.isEmpty() || ap.isEmpty() || am.isEmpty() || direc.isEmpty() || correo.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean resultado = usuarioDAO.insertarUsuario(nombre, ap, am, direc, correo, tel);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Usuario guardado con éxito!");
            limpiarCampos();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        TxtNombre.setText("");
        TxtAP.setText("");
        TxtAM.setText("");
        TxtDirec.setText("");
        TxtCorreo.setText("");
        TxtTel.setText("");
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = usuarioDAO.obtenerUsuarioModel();
        tblUsuarios.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal());
    }
}
