package curso.cap.tagsPractice.tags;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ValorTag extends TagSupport {
	
	private int campo;

	@Override
	public int doStartTag() throws JspException {
		return doEndTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		//necesitas de la etiqueta que te envuelve (=ResultadoBodyTag) la propiedad ResultSet
		//y de este ResultSet vas sacando los valores para cada campo
		try {
			ResultadoBodyTag padre = (ResultadoBodyTag) findAncestorWithClass(this, Class.forName("curso.cap.tagsPractice.tags.ResultadoBodyTag"));
			//y luego hay que escribir el contenido
			//usas el jspWriter de tagSupport para imprimir el valor de cada campo(guardarlo en el buffer)
			pageContext.getOut().println(padre.getResultSet().getString(getCampo()));
			//y evaluas y guardas el buffer
			return EVAL_PAGE;
		} catch (ClassNotFoundException | SQLException | IOException e) {
			//tienes que cerrar la conexion aquí pq no va a seguir por el .jsp 
			//y entonces no llegará al final de la etiqueta ConexionBodyTag (dos etiq. por encima) que es la que lo cerraría si todo oka
			try {
				ConexionBodyTag abuelo = (ConexionBodyTag) findAncestorWithClass(this, Class.forName("curso.cap.tagsPractice.tags.ConexionBodyTag"));
				abuelo.getConexion().close();
			} catch (ClassNotFoundException | SQLException e1) {
				
			}
			return SKIP_PAGE;//seguiría por el jsp y llegaría al endTag() de conexionbodytag que cierra la conexion ahí
			
		}
		
	}


}
