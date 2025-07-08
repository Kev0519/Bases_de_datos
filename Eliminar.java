import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Eliminar extends JFrame {

    private JTable tablaUsuarios;
    private JTextField txtId;
    private JButton btnEliminar, btnCancelar;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Eliminar() {
        setTitle("Eliminar Usuarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblId = new JLabel("ID a eliminar:");
        lblId.setBounds(30, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 100, 25);
        add(txtId);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(250, 20, 100, 25);
        add(btnEliminar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(370, 20, 100, 25);
        add(btnCancelar);

        tablaUsuarios = new JTable();
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBounds(30, 70, 520, 250);
        add(scroll);

        actualizarTabla();

        // Evento: clic en fila de la tabla
        tablaUsuarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int fila = tablaUsuarios.rowAtPoint(evt.getPoint());
                if (fila >= 0) {
                    txtId.setText(tablaUsuarios.getValueAt(fila, 0).toString());
                }
            }
        });

        // Evento: eliminar
        btnEliminar.addActionListener(e -> eliminarUsuario());

        // Evento: cancelar
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void eliminarUsuario() {
        String idTexto = txtId.getText().trim();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un ID.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(idTexto);
            boolean eliminado = usuarioDAO.eliminarUsuarioPorId(id);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                actualizarTabla();
                txtId.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        tablaUsuarios.setModel(usuarioDAO.obtenerUsuarioModel());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Eliminar());
    }
}
