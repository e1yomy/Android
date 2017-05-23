package elyo.my.trackids;

/**
 * Created by elyo_ on 03/05/2017.
 */

public class Usuario {

    String id;
    String usuario;
    String nombres;
    String apellidos;
    String telefono;
    String contrasena;
    String pin;
    public Usuario(){

    }
    public Usuario(String id,String usuario, String nombres, String apellidos, String telefono, String contrasena, String pin)
    {
        this.id=id;
        this.usuario=usuario;
        this.nombres=nombres;
        this.apellidos=apellidos;
        this.telefono=telefono;
        this.contrasena=contrasena;
        this.pin=pin;
    }


}
