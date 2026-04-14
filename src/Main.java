import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "RIBERA";
        String password = "ribera";
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("1. Nuevo Ciclista:");
            System.out.println("2. Actualizar Ciclista:");
            System.out.println("3. Eliminar Ciclista:");
            System.out.println("4. Mostrar Ciclista:");
            System.out.println("5. Salir:");
            System.out.println("Elija una opcion:");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:

                    String SQLID = "SELECT MAX(ID_CICLISTA) as maxId FROM Ciclista";
                    int id =0;
                    System.out.println("Ingrese el nombre del ciclista:");
                    String nombre = sc.next();
                    System.out.println("Ingrese el edad del ciclista:");
                    int edad = sc.nextInt();
                    System.out.println("Ingrese la nacionalidad del ciclista:");
                    String nacionalidad = sc.next();
                    System.out.println("Ingrese el equipo del ciclista (1-5):");
                    int equipo = sc.nextInt();
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         Statement statement = conn.createStatement()) {
                        ResultSet resultado = statement.executeQuery(SQLID);
                        if (resultado.next()) {
                            id = resultado.getInt("maxId") + 1;
                        }

                        String SQLInsertar = "INSERT INTO ciclista (id_ciclista, nombre, edad, nacionalidad, id_equipo) VALUES (?,?,?,?,?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(SQLInsertar);

                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, nombre);
                        preparedStatement.setInt(3, edad);
                        preparedStatement.setString(4, nacionalidad);
                        preparedStatement.setInt(5, equipo);
                        preparedStatement.executeUpdate();
                    }catch (SQLException e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                    System.out.println("Ciclista agregado correctamente");
                    break;
                case 2:
                    System.out.println("Ingrese el id del ciclista:");
                    int ID=sc.nextInt();
                    System.out.println("Ingrese la edad del ciclista:");
                    int Edad=sc.nextInt();
                    System.out.println("Ingrese el equipo del ciclista (1-5):");
                    int Equipo=sc.nextInt();
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         Statement statement = conn.createStatement()){
                        String SQLActualizar ="UPDATE ciclista SET edad=?, ID_EQUIPO=? WHERE id_ciclista=?";
                        PreparedStatement preparedStatement = conn.prepareStatement(SQLActualizar);
                        preparedStatement.setInt(1, Edad);
                        preparedStatement.setInt(2, Equipo);
                        preparedStatement.setInt(3, ID);
                        preparedStatement.executeUpdate();
                    }catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    System.out.println("Ciclista actualizado correctamente");
                    break;
                case 3:
                    System.out.println("Ingrese el id del ciclista:");
                    int eliminar = sc.nextInt();
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                         Statement statement = conn.createStatement()) {
                        String SQLEliminar = "DELETE FROM ciclista WHERE ID_CICLISTA = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(SQLEliminar);

                        preparedStatement.setInt(1,eliminar);
                        preparedStatement.executeUpdate();
                    }catch (SQLException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    System.out.println("Ciclista eliminado correctamente");
                    break;
                case 4:
                    try (Connection conn = DriverManager.getConnection(url, user, password);
                            Statement statement = conn.createStatement()) {
                    String sql = "SELECT C.ID_CICLISTA AS IDCICLISTA, C.NOMBRE, C.NACIONALIDAD, C.EDAD, E.NOMBRE AS EQUIPO " +
                            "FROM CICLISTA C " +
                            "JOIN EQUIPO E ON C.ID_EQUIPO = E.ID_EQUIPO ";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                        try (ResultSet rs = preparedStatement.executeQuery()) {
                            while (rs.next()) {
                                int Id = rs.getInt("IDCICLISTA");
                                String CICLISTA = rs.getString("NOMBRE");
                                String NACIONALIDAD = rs.getString("NACIONALIDAD");
                                int EDAD = rs.getInt("EDAD");
                                String EQUIPO = rs.getString("EQUIPO");

                                System.out.println("ID: " + Id + ", Nombre: " + CICLISTA + ", Nacionalidad: " + NACIONALIDAD + ", Edad: " + EDAD + ", Equipo: " + EQUIPO);
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (opcion != 5);

    }
}

