package com.hosnag.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author ccarreno
 */
public class App {

    //args[0] = ip_servidor
    //args[1] = puerto_servidor
    //args[2] = nombre_bd
    //args[3] = usuario_bd
    //args[4] = contraseña_bd
    //args[5] = empresa
    //args[6] = año factura
    //args[7] = cod_doc
    //args[8] = localidad
    //args[9] = punto de emisión
    //args[10] = numero de factura
    //args[11] = path
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Connection conn = conectar("servidor", "puerto", "base de datos", "usuario", "contraseña");
        saveFile("1", "2015","01", "001", "003", "000000777", "C:\\factura.XML", conn);
                
        //Connection conn = conectar(args[0], args[1], args[2], args[3], args[4]);
        
        System.out.println("Conexión: " + args[0] +" "+ args[1] +" "+ args[2] +" "+ args[3] +" "+ args[4]);
        
        //saveFile(args[5], args[6], args[7],args[8],args[9], args[10], args[11], conn);
    }
    
    public void setFile() {
         /*     FileInputStream fileInputStream = null;
        
        try {
 
	    //convert array of bytes into file
	    FileOutputStream fileOuputStream = 
                  new FileOutputStream("C:\\Users\\ccarreno\\Desktop\\FacturacionElectronica\\1-001-018000000666.XML"); 
	    fileOuputStream.write(getFileBD());
	    fileOuputStream.close();
 
	    System.out.println("Done");
            
        }catch(Exception e){
            e.printStackTrace();
        }*/
}
    
    
    public static byte[] getFileBD(){
    /*    
        byte[] imgBytes = null;
        
        try{
        
        //PreparedStatement ps = conectar().prepareStatement("SELECT archivo FROM table_borrar WHERE nombre = ?");
        ps.setString(1, "1-001-018000000392.XML");
        ResultSet rs = ps.executeQuery();
     
        while (rs.next()) {    
            imgBytes = rs.getBytes(1);    
    }
                
        rs.close();
        ps.close();
        
        return imgBytes;
        
        }catch(SQLException e){
                System.out.println(e.getMessage());
                return null;
        }*/
    
    return null;
    }
    
    
    public static void saveFile(String var_empresa,
                                String var_anio_factura,
                                String var_cod_doc,
                                String var_localidad,
                                String var_punto_de_emision,
                                String var_numero_factura,
                                String path, 
                                Connection conn){   
        
        System.out.println("Variables: " + var_empresa + " " +
                                var_anio_factura + " " +
                                var_cod_doc + " " +
                                var_localidad + " " +
                                var_punto_de_emision + " " +
                                var_numero_factura + " " +
                                path);
 
        try{
        
        //File file = new File("C:\\Users\\ccarreno\\Desktop\\FacturacionElectronica\\1-001-018000000669.XML");
        File file = new File(path);
        
            System.out.println("###### Ejecutando Update en el PostgreSQL... Pilas Ivette ######");
            
        System.out.println("Verificando si archivo xml existe..");
        
        if(file.exists()){
        
            System.out.println("Archivo si existe");
            
        FileInputStream fis = new FileInputStream(file);
               
        String sql = "{ call publico.funcion_insert_archivo(?,?,?,?,?,?,?,?) }";

        System.out.println("SQL: " + sql);
        
        CallableStatement cs = conn.prepareCall(sql);
        
        cs.setInt(1, Integer.parseInt(var_empresa));
        System.out.println("empresa: " + var_empresa);
        cs.setInt(2, Integer.parseInt(var_anio_factura));
        System.out.println("anio_factura: " + var_anio_factura);
        cs.setString(3, var_cod_doc);
        System.out.println("cod_doc: " + var_cod_doc);
        cs.setString(4, var_localidad);
        System.out.println("localidad: " + var_localidad);
        cs.setString(5, var_punto_de_emision);
        System.out.println("punto emision: " + var_punto_de_emision);
        cs.setString(6, var_numero_factura);
        System.out.println("numero factura: " + var_numero_factura);
        cs.setString(7, file.getName());
        System.out.println("nombre archivo: " + file.getName());
        cs.setBinaryStream(8, fis, (int)file.length());
        System.out.println("binario: " + (int)file.length());
        
        
        if(cs.execute())
            System.out.println("Grabado satisfactoriamente...");
        else
            System.out.println("No se ejecuto correctamente...");
        
        cs.close();
        fis.close();
        conn.close();
        
            System.out.println("Finalizado...");
       
        }else{
            
            System.out.println("No se encontro archivo o no existe...");    
        }

        }catch(IOException io){
            System.out.println(io.getMessage());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    } 
	
	public static Connection conectar(String servidor, String port, String bd, String usuario, String pass){
		
		Calendar Cal= Calendar.getInstance(); 
		String fecha = Cal.get(Cal.DATE)+"/"+(Cal.get(Cal.MONTH)+1)+"/"+Cal.get(Cal.YEAR)+" "+Cal.get(Cal.HOUR_OF_DAY)+":"+Cal.get(Cal.MINUTE)+":"+Cal.get(Cal.SECOND); 
		
		boolean conexion = false;
		Connection con = null;        
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://" + servidor + ":" + port + "/"+bd+"",""+usuario+"", ""+pass+"");
            
            System.out.println("Conectado a la BD fecha: " + fecha);
            conexion = true;
        }catch(Exception e){
        	conexion = false;
        	System.out.println("No se pudo conectar a la BD" + fecha + " Error: " + e);
        }
        return con;
    }
    
}
