import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {
    public Statement stm;
    public ResultSet rs;
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String caminho = "jdbc:sqlserver://localhost:1433;/" + "databaseName=sistemasdistribuidos";
    private String usuario = "jhoni";
    private String senha = "@Joao3257";
    private Connection con;

    public Connection Conexao() {
        try {
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=sistemasdistribuidos;user=joao.santos;password=joao3257;encrypt=false");
            System.out.println("Conectou");
            return con;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return con;
    }
}
