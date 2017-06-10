package elyo.my;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

public class Skins extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Skin skin;
	Stage stage1,stage3,stage2,stage, stage4;
	Array<String> datos;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		skin = new Skin(Gdx.files.internal("plain-james/skin/plain-james-ui.json"));
		stage=new Stage();
		datos=new Array<String>();
		crearPantalla();
		//crearPantalla2();
		//crearPantalla3();
		//crearPantalla4();
		Gdx.input.setInputProcessor(stage);

	}
	public void crearPantalla(){

		stage=new Stage();
		Label lblEtiqueta1= new Label("Usuario",skin);
		final TextField txtTexto1 = new TextField("",skin);
		Label lblEtiqueta2= new Label("Contrasenia",skin);
		final TextField txtTexto2 = new TextField("",skin);
		txtTexto2.setPasswordMode(true);
		txtTexto2.setPasswordCharacter('*');

		TextButton btnBoton = new TextButton("Iniciar sesion",skin);
		//stagePrueba.addActor(lblEtiqueta);
		//stagePrueba.addActor(txtTexto);
		//stagePrueba.addActor(btnBoton);
		Table tabla = new Table();
		tabla.center();
		tabla.add(lblEtiqueta1);tabla.row();
		tabla.add(txtTexto1);tabla.row();
		tabla.add(lblEtiqueta2);tabla.row();
		tabla.add(txtTexto2);tabla.row();
		tabla.add(new Label("",skin));tabla.row();
		tabla.add(btnBoton);
		tabla.setFillParent(true);
		tabla.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		stage.addActor(tabla);
		btnBoton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//txtTexto.setText("Erik puto");
				if(txtTexto1.getText()!="" && txtTexto2.getText()!="")
				crearPantalla2();

			}
		});
		//lblEtiqueta.setPosition(100,200);
		//txtTexto.setPosition(100,100);
		//btnBoton.setPosition(100,50);

	}
	public void crearPantalla2(){
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		Array<String> opciones= new Array<String>();
		opciones.add("Seleccionar");
		opciones.add("Estudiante");
		opciones.add("Conductor");
		opciones.add("Estudiante");
		opciones.add("Vendedor");
		Label lblNombre= new Label("Nombre",skin);
		final TextField txtNombre = new TextField("",skin);
		Label lblSexo= new Label("Sexo",skin);
		final CheckBox cckSexo1 = new CheckBox("Masculino",skin);
		final CheckBox cckSexo2 = new CheckBox("Femenino",skin);
		Label lblOcupacion= new Label("Ocupacion",skin);
		final SelectBox sbOcupacion=new SelectBox(skin);
		sbOcupacion.setItems(opciones);
		TextButton btnBoton2 = new TextButton("Seguir",skin);
		Table tabla = new Table();
		tabla.center();
		tabla.add(lblNombre);tabla.row();
		tabla.add(txtNombre);tabla.row();
		tabla.add(new Label("",skin));tabla.row();
		tabla.add(lblSexo);tabla.row();
		tabla.add(cckSexo1);
		tabla.add(cckSexo2);tabla.row();
		tabla.add(new Label("",skin));tabla.row();
		tabla.add(lblOcupacion);tabla.row();
		tabla.add(sbOcupacion);tabla.row();
		tabla.add(new Label("",skin));tabla.row();
		tabla.add(btnBoton2);
		tabla.setFillParent(true);
		tabla.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		stage.addActor(tabla);
		btnBoton2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if( txtNombre.getText()!="" && cckSexo1.isChecked() &&!cckSexo2.isChecked() || !cckSexo1.isChecked() &&cckSexo2.isChecked() && sbOcupacion.getSelected().toString()!="Seleccionar") {
					datos.add("Nombre: "+txtNombre.getText());
					if(cckSexo1.isChecked())
						datos.add("Sexo: "+ cckSexo1.getText().toString());
					else
						datos.add("Sexo: "+ cckSexo2.getText().toString());
					datos.add("Ocupacion: "+sbOcupacion.getSelected().toString());
					crearPantalla3();
				}

			}
		});

	}
	public void crearPantalla3(){
		stage= new Stage();
		Gdx.input.setInputProcessor(stage);
		Array<String> opciones= new Array<String>();
		opciones.add("Deportes");
		opciones.add("Entretenimiento");
		opciones.add("Negocios");
		opciones.add("Noticias");
		opciones.add("Educacion");

		Label lblIntereses= new Label("Intereses",skin);
		final List lisIntereses=new List(skin);
		lisIntereses.setItems(opciones);
		TextButton btnBoton3 = new TextButton("Fin",skin);
		Table tabla = new Table();
		tabla.center();
		tabla.add(lblIntereses);tabla.row();
		tabla.add(lisIntereses);tabla.row();
		tabla.add(new Label("",skin));tabla.row();
		tabla.add(btnBoton3);
		tabla.setFillParent(true);
		tabla.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		stage.addActor(tabla);
	btnBoton3.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			datos.add("Intereses: "+lisIntereses.getSelected().toString());
			crearPantalla4();
		}
		});


	}
	public void crearPantalla4(){
		stage=new Stage();

		Label lblNombre = new Label(datos.get(0),skin);
		Label lblsexo = new Label(datos.get(1),skin);
		Label lblOcupacion = new Label(datos.get(2),skin);
		Label lblIntereses = new Label(datos.get(3),skin);

		Table tabla = new Table();
		tabla.center();
		tabla.add(lblNombre);tabla.row();
		tabla.add(lblsexo);tabla.row();
		tabla.add(lblOcupacion);tabla.row();
		tabla.add(lblIntereses);tabla.row();

		tabla.setFillParent(true);
		tabla.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		stage.addActor(tabla);
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();

		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

