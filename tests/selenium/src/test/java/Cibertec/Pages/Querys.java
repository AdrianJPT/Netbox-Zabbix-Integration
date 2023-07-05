package Cibertec.Pages;


import net.thucydides.core.annotations.Step;

import java.sql.*;
import java.util.Objects;

public class Querys {

    String CERMI_jdbcUrl = "jdbc:oracle:thin:@SVRCERMI.cibertec.edu.pe:1521:CERMI?characterEncoding=utf8";
    String CERMI_username_GENERAL = "GENERAL";
    String CERMI_username_WSPWORK03 = "WSPWORK03";
    String CERMI_password = "mc_$Rt3R$t";


    String CERSPRING_jdbcUrl = "jdbc:sqlserver://10.143.128.135:1433;databaseName=CIB_CERT";
    String CERSPRING_username = "WSARM03";
    String CERSPRING_password = "rPmc8$2023$";

    @Step
    public void NotificacionUpdate(String codigo) throws SQLException {

        System.out.println("Opening a connection xxxxxxxxxxxxxxxxx");
        //1 crear     coneccion
        try {
            // Cargar el controlador JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(CERMI_jdbcUrl, CERMI_username_GENERAL, CERMI_password);
            //jdbc:oracle:thin:@//SVRCERMI.cibertec.edu.pe:1521/CERMI

            // Create Statement / query

            Statement statement = connection.createStatement();

            String res = "  UPDATE USUARIO US" +
                    "  SET US.EMAIL='adrian.pablo@laureate.pe',US.EMAIL_ALTERNO='adrian.pablo@laureate.pe'" +
                    "  WHERE COD_USUARIO IN ('I"+ codigo +"')";

            // Realizar operaciones en la base de datos...
            statement.execute(res);

            // Cerrar la conexión
            connection.close();

            System.out.println("CORRECTAMENTEE ****************--------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String ESTADO_SOLICITUD;
    public String EVALUADOR = "0";


    public void Reporte_Tramite(String cod_tramite)  //cod_tramite = SOTRAGE_CARNEMEDIO
    {



        System.out.println("REPORTE xxxxxxxxxxxxxxxxx");


        String query = "\n" +
                "  SELECT \n" +
                "  \n" +
                " CAMPO1 AS \"MODALIDAD\"\n" +
                ",C.COD_PERIODO AS \"PERIODO\"\n" +
                ",CAMPO3 AS \"CODALUMNO\"\n" +
                ",CAMPO8 AS \"APELLIDO PATERNO\"  --ApellidoPatern\n" +
                ",CAMPO9 AS \"APELLIDO MATENO\" --ApellidoMatern\n" +
                ",CAMPO7 AS \"NOMBRES\"  --Nombres\n" +
                ",nvl(TO_CHAR(G.FECHA_PAGO, 'dd/mm/YYYY'), '')as \"FECHA DE PAGO\"\n" +
                ",case when G.FECHA_PAGO=NULL THEN 'PENDIENTE DE PAGO' ELSE 'PAGADO' END AS \"ESTADO DE PAGO\"\n" +
                ",f.campo17 as \"CORREO\"\n" +
                ",a.ID_SOLICITUD as \"NUMERO DE SOLICITUD\"\n" +
                ",D.COD_TRAMITE AS \"CODTRAMITE\"\n" +
                ",TO_CHAR(a.fecha_creacion, 'dd/mm/YYYY') AS \"FECHA DE SOLICITUD DE REGISTRO\"\n" +
                ",F.CAMPO6 AS \"COD CARRERA\"\n" +
                ",f.CAMPO34 as \"DESCRIPCION CARRERA\"\n" +
                ",(SELECT DESCRIPCION FROM WSPWORK03.WFW_ESTADO WHERE COD_ESTADO=A.COD_ESTADO) AS \"ESTADO_SOLICITUD\"\n" +
                ",Q.NOMBRE AS NOMBRE_ACTIVIDAD\n" +
                ",(SELECT DESCRIPCION FROM WSPWORK03.WFW_ESTADO WHERE COD_ESTADO=E.COD_ESTADO) AS \"ESTADO_ACTIVIDAD\"\n" +
                "    ,e.usuario_evaluador as EVALUADOR\n" +
                "    FROM WSPWORK03.WFW_SOLICITUD A\n" +
                "    INNER JOIN WSPWORK03.WFW_PERIODO_TRAMITE C ON A.ID_PERIODO=C.ID_PERIODO\n" +
                "    INNER JOIN WSPWORK03.WFW_TRAMITE D ON C.COD_TRAMITE=D.COD_TRAMITE\n" +
                "    INNER JOIN WSPWORK03.WFW_DETALLE_SOLICITUD F ON A.COD_SOLICITUD=F.COD_SOLICITUD \n" +
                "    INNER JOIN WSPWORK03.WFW_DETALLE_PAGO_SOLICITUD P ON A.COD_SOLICITUD = P.COD_SOLICITUD\n" +
                "    INNER JOIN WSPWORK03.WFW_EVALUACION_SOL E ON A.COD_SOLICITUD=E.COD_SOLICITUD \n" +
                "    INNER JOIN WSPWORK03.WFW_ACTIVIDAD Q ON Q.COD_ACTIVIDAD=E.COD_ACTIVIDAD\n" +
                "    INNER JOIN wspwork03.WFW_DETALLE_PAGO_SOLICITUD G ON g.cod_solicitud=a.cod_solicitud\n" +
                "\n" +
                "    WHERE D.COD_TRAMITE = ? \n" +
                "    AND Q.ORDEN_ACTIVIDAD = ( SELECT MAX(ACT.ORDEN_ACTIVIDAD) AS ORDEN\n" +
                "                          FROM WSPWORK03.WFW_SOLICITUD SOL\n" +
                "                          INNER JOIN WSPWORK03.WFW_EVALUACION_SOL EVAL ON SOL.COD_SOLICITUD=EVAL.COD_SOLICITUD \n" +
                "                          INNER JOIN WSPWORK03.WFW_ACTIVIDAD ACT ON EVAL.COD_ACTIVIDAD=ACT.COD_ACTIVIDAD\n" +
                "                          WHERE \n" +
                "                          SOL.ID_SOLICITUD=A.ID_SOLICITUD)\n" +
                " AND TO_DATE(A.FECHA_CREACION,'DD/MM/YY') BETWEEN TO_DATE('01/10/2022', 'dd/mm/yy') AND TO_DATE('30/12/2050', 'dd/mm/yy') -- @FECHA_INICIO Y @FECHA_FIN\n" +
                "    ORDER BY A.ID_SOLICITUD DESC";

        try {
            Connection connection = DriverManager.getConnection(CERMI_jdbcUrl, CERMI_username_WSPWORK03, CERMI_password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cod_tramite);

            boolean evaluadorUpdated = false;
            EVALUADOR = null;

            while (!evaluadorUpdated) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String evaluador = resultSet.getString("EVALUADOR");

                    if (!evaluador.equals("0")) {
                        evaluadorUpdated = true;
                        EVALUADOR = evaluador;
                    }
                    resultSet.close();
                } else {
                    System.out.println("No se encontraron registros");
                    break;
                }


                // Pausa antes de realizar la siguiente consulta
                Thread.sleep(10000); // Pausa de 10 segundo (ajusta el tiempo según tus necesidades)
            }

            System.out.println("EVALUADOR: " + EVALUADOR);
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    // SIMULADOR DE PAGOS
    //-- PAGO
    String numDocumento;
    Double pImportePagado;

    // -- ANULAR PAGO
    public void TraerDeuda(String codigo) {

        System.out.println("TRAER DEUDA xxxxxxxxxxxxxxxxx");
        String query = "exec dbo.COp_ConsultarDeuda @pCodigoProducto='003',@pTipoConsulta='1',@pIdConsulta='i" + codigo +"'";

        try {
            Connection connection = DriverManager.getConnection(CERSPRING_jdbcUrl, CERSPRING_username, CERSPRING_password);
            CallableStatement callableStatement = connection.prepareCall(query);
            boolean result = callableStatement.execute();

            if (result) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    numDocumento = resultSet.getString("NumDocumento");
                    pImportePagado = resultSet.getDouble("Deuda");

                }
                resultSet.close();
            }

            System.out.println("NumDocumento: " + numDocumento);
            System.out.println("pImportePagado: " + pImportePagado);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void PagarDeuda(String codigo, String numDocumento,Double pImportePagado)  {
        String pImportePagado_formateado = String.format("%.2f", pImportePagado);


        System.out.println("PAGAR DEUDA xxxxxxxxxxxxxxxxx");


        String query = "exec dbo.COp_NotificarPago @pFechaTxn='12062023', @pHoraTxn='18:04:18', @pCanalPago='10', " +
                "@pCodigoBanco='002', @pNumOperacionBanco='000006289651', @pFormaPago='01', @pTipoConsulta='1', " +
                "@pIdConsulta='i" + codigo + "', @pCodigoProducto='003', @pNumDocumento='"+ numDocumento +"', " +
                "@pImportePagado="+pImportePagado_formateado +", @pMonedaDoc='1', @pFechaTxnLogica='12062023', " +
                "@pCuentaBancaria='000-4272421'";

        try {
            Connection connection = DriverManager.getConnection(CERSPRING_jdbcUrl, CERSPRING_username, CERSPRING_password);
            CallableStatement callableStatement = connection.prepareCall(query);
            boolean result = callableStatement.execute();

            if (result) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    String valorPrimeraColumna = resultSet.getString(1);
                }
                resultSet.close();
            }

            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void realizarPago(String codigo)  {
        TraerDeuda(codigo);
        PagarDeuda(codigo,numDocumento,pImportePagado);

    }
}
