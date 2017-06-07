package transparencia.itai.com.transparenciadigital;

/**
 * Created by elyo_ on 05/06/2017.
 */

public class SolicitudItem {
    int id;
    String sujetoObligado;
    String fecha;
    int estado;
    int tipo;
    public SolicitudItem(int tipo,int id, String sujetoObligado, String fecha, int estado)
    {
        this.tipo=tipo;
        this.id=id;
        this.sujetoObligado=sujetoObligado;
        this.fecha=fecha;
        this.estado=estado;
    }
    public void EnviarARecurso()
    {
        if(tipo==0)
        {
            //Funcion del servicio web y pasa a recurso de revision
        }
        else
        {
            return;
        }
    }
    public void ObtenerDetalles()
    {
        String tabla="";
        switch (tipo)
        {
            case 0:
                tabla="solAcceso";
                break;
            case 1:
                tabla="recRevision";
                break;
            case 2:
                tabla="demIncumplimiento";
                break;
        }
        //Enviar solicitud de datos con la tabla seleccionada y el this.id

    }


}

