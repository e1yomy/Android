package transparencia.itai.com.transparenciadigital;

import java.util.ArrayList;

/**
 * Created by elyo_ on 24/04/2017.
 */

public class Usuario {

    String id,
            rol,

            correo,
            contrasena,
            nombres,
            paterno,
            materno,
            calle,
            noExterno,
            noInterno,
            entreCalles,
            colonia,
            cp,
            entidadFederativa,
            municipio,
            telefono;
    ArrayList<String> datos;
    public Usuario(String id, String rol, String correo, String contrasena, String nombres, String paterno, String materno, String calle, String noExterno, String noInterno, String entreCalles, String colonia, String cp, String entidadFederativa, String municipio, String telefono){
        this.id= id;
        this.rol= rol;
        this.correo= correo;
        this.contrasena= contrasena;
        this.nombres= nombres;
        this.paterno= paterno;
        this.materno= materno;
        this.calle= calle;
        this.noExterno= noExterno;
        this.noInterno= noInterno;
        this.entreCalles= entreCalles;
        this.colonia= colonia;
        this.cp= cp;
        this.entidadFederativa= entidadFederativa;
        this.municipio= municipio;
        this.telefono= telefono;
        this.datos= new ArrayList<>();
        datos.add(id);
        datos.add(rol);
        datos.add(correo);
        datos.add(contrasena);
        datos.add(nombres);
        datos.add(paterno);
        datos.add(materno);
        datos.add(calle);
        datos.add(noExterno);
        datos.add(noInterno);
        datos.add(entreCalles);
        datos.add(colonia);
        datos.add(cp);
        datos.add(entidadFederativa);
        datos.add(municipio);
        datos.add(telefono);

    }
    public Usuario(){
    }
    public int GuardarUsuario(String id, String rol, String correo, String contrasena, String nombres, String paterno, String materno, String calle, String noExterno, String noInterno, String entreCalles, String colonia, String cp, String entidadFederativa, String municipio, String telefono){
        Conexion conexion= new Conexion();
        int a= conexion.RegistrarUsuario(id,rol,correo,contrasena,nombres,paterno,materno,calle,noExterno,noInterno,entreCalles,colonia,cp,entidadFederativa,municipio,telefono);


        return 1;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
            this.id = id;
        }
    public String getRol() {
            return rol;
        }
    public void setRol(String rol) {
            this.rol = rol;
        }
    public String getCorreo() {
            return correo;
        }
    public void setCorreo(String correo) {
            this.correo = correo;
        }
    public String getContrasena() {
            return contrasena;
        }
    public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    public String getNombres() {
            return nombres;
        }
    public void setNombres(String nombres) {
            this.nombres = nombres;
        }
    public String getPaterno() {
            return paterno;
        }
    public void setPaterno(String paterno) {
            this.paterno = paterno;
        }
    public String getMaterno() {
            return materno;
        }
    public void setMaterno(String materno) {
            this.materno = materno;
        }
    public String getCalle() {
            return calle;
        }
    public void setCalle(String calle) {
            this.calle = calle;
        }
    public String getNoExterno() {
            return noExterno;
        }
    public void setNoExterno(String noExterno) {
            this.noExterno = noExterno;
        }
    public String getNoInterno() {
            return noInterno;
        }
    public void setNoInterno(String noInterno) {
            this.noInterno = noInterno;
        }
    public String getEntreCalles() {
            return entreCalles;
        }
    public void setEntreCalles(String entreCalles) {
            this.entreCalles = entreCalles;
        }
    public String getColonia() {
            return colonia;
        }
    public void setColonia(String colonia) {
            this.colonia = colonia;
        }
    public String getCp() {
            return cp;
        }
    public void setCp(String cp) {
            this.cp = cp;
        }
    public String getEntidadFederativa() {
            return entidadFederativa;
        }
    public void setEntidadFederativa(String entidadFederativa) {
            this.entidadFederativa = entidadFederativa;
        }
    public String getMunicipio() {
            return municipio;
        }
    public void setMunicipio(String municipio) {
            this.municipio = municipio;
        }
    public String getTelefono() {
            return telefono;
        }
    public void setTelefono(String telefono) {
            this.telefono = telefono;
        }


}
