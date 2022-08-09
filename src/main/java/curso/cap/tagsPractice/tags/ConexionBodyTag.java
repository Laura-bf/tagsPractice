package curso.cap.tagsPractice.tags;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import lombok.Getter;
import lombok.Setter;

/*
 * haces las etiquetas que heredan de bodytagsupport o tagsupport
 * las añades al .tld (taglibrarydescription)
 * luego, el servlet (el archivo .jsp es un servlet) monta la página
 * haciéndolo así, en el servlet ya no hay nada de código java
 */

@SuppressWarnings("serial")
@Getter
@Setter
public class ConexionBodyTag extends BodyTagSupport {

	private String driver,cadena,usuario,clave;
	private Connection conexion; //para luego poder usarla en las entiquetas hijas
	
	@Override
	public int doStartTag() throws JspException {
		try {
			Class.forName(getDriver());
			setConexion(DriverManager.getConnection(getCadena(), getUsuario(), getClave()));
			return EVAL_BODY_BUFFERED;
		} catch (ClassNotFoundException | SQLException e) {
			try {
				conexion.close();
				return SKIP_PAGE;
			} catch (SQLException e1) {
				
			}
			return SKIP_PAGE;
		}
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			getConexion().close();
			getBodyContent().writeOut(getPreviousOut());
			return EVAL_PAGE;
		} catch (SQLException | IOException e) {
			return SKIP_PAGE;
		}
		
		
	}
	
	
	
	
}
