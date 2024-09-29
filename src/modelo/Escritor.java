
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.frmEscritores;

public class Escritor {
    
    private String Nombre;
    private int Edad;
    private double Peso;
    private String Correo;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double Peso) {
        this.Peso = Peso;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    
    
    
    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = Conexion.getConexion();
        try {
            //Variable que contiene la Query a ejecutar
            String sql = "INSERT INTO tbEscritor(UUID_Escritor, Nombre_Escritor, Edad_Escritor, Peso_Escritor, Correo_Escritor) VALUES (?, ?, ?, ?, ?)";
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            //Establecer valores de la consulta SQL
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, getNombre());
            pstmt.setInt(3, getEdad());
            pstmt.setDouble(4, getPeso());
            pstmt.setString(5, getCorreo());     

            //Ejecutar la consulta
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
    
    
     public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = Conexion.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID", "Nombre", "Edad", "Peso", "Correo"});
        try {
            //Consulta a ejecutar
            String query = "SELECT * FROM tbEscritor";
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet rs = statement.executeQuery(query);
            //Recorremos el ResultSet
            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{rs.getString("UUID_Escritor"), 
                    rs.getString("Nombre_Escritor"), 
                    rs.getInt("Edad_Escritor"), 
                    rs.getInt("Peso_Escritor"),     
                    rs.getString("Correo_Escritor")});
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }
     
     public void Eliminar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = Conexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        //Obtenemos el id de la fila seleccionada

        String miUUID = tabla.getValueAt(filaSeleccionada, 0).toString();
        //borramos 
        try {
            String sql = "delete from tbEscritor where UUID_Escritor = ?";
            PreparedStatement deleteEscritor = conexion.prepareStatement(sql);
            deleteEscritor.setString(1, miUUID);
            deleteEscritor.executeUpdate();
        } catch (Exception e) {
            System.out.println("este es el error metodo de eliminar" + e);
        }
    }
     
     public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = Conexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();

            try {
                //Ejecutamos la Query
                String sql = "update tbEscritor set Nombre_Escritor= ?, Edad_Escritor = ?, Peso_Escritor = ?, Correo_Escritor = ? where UUID_Escritor = ?";
                PreparedStatement updateEscritor = conexion.prepareStatement(sql);

                updateEscritor.setString(1, getNombre());
                updateEscritor.setInt(2, getEdad());
                updateEscritor.setDouble(3, getPeso());
                updateEscritor.setString(4, getCorreo());
                updateEscritor.setString(5, miUUId);
                updateEscritor.executeUpdate();

            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } else {
            System.out.println("no");
        }
    }
     
      public void limpiar(frmEscritores vista) {
        vista.txtEscritor.setText("");
        vista.txtEdad.setText("");
        vista.txtPeso.setText("");
        vista.txtCorreo.setText("");
    }

    public void cargarDatosTabla(frmEscritores vista) {
        // Obtén la fila seleccionada 
        int filaSeleccionada = vista.tbEscritores.getSelectedRow();

        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String NombreDeTb = vista.tbEscritores.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = vista.tbEscritores.getValueAt(filaSeleccionada, 2).toString();
            String PesoDeTb = vista.tbEscritores.getValueAt(filaSeleccionada, 3).toString();
            String CorreoDeTB = vista.tbEscritores.getValueAt(filaSeleccionada, 4).toString();

            // Establece los valores en los campos de texto
            vista.txtEscritor.setText(NombreDeTb);
            vista.txtEdad.setText(EdadDeTb);
            vista.txtPeso.setText(PesoDeTb);
            vista.txtCorreo.setText(CorreoDeTB);
        }
    }
  
}
