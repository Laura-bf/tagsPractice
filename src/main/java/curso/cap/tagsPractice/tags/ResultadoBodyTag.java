package curso.cap.tagsPractice.tags;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResultadoBodyTag extends BodyTagSupport {

	private String sql;
	private ResultSet resultSet; // para luego poder usarlo en las etiquetas hijas

	@Override
	public int doStartTag() throws JspException {
		// coges la conexion creada, que es una propiedad de la etiqueta padre de esta
		// etiqueta
		// en el .jsp, datos:resultado está dentro de datos:conexion, y esta última
		// tiene un atributo Connection
		/*
		 * hay dos formas, si es la etiqueta inmediatamente superior (la llamamos:
		 * padre) ConexionBodyTag padre = (ConexionBodyTag) getParent(); si la que tiene
		 * lo que necesitas está más arriba usas: findAncestorWithClass(this,
		 * Class.forName("package.NombreClaseEtiqueta")
		 */
		try {
			// coges la instancia de la etiqueta superior:
			ConexionBodyTag padre = (ConexionBodyTag) findAncestorWithClass(this,
					Class.forName("curso.cap.tagsPractice.tags.ConexionBodyTag"));
			// coges la conexion que está en el buffer después de la etiqueta anterior
			Connection con = padre.getConexion();
			// haces la query sql (esta sql querie estará en el .jsp) y el resultado lo
			// guardas en el atributo resultSet de la etiqueta con un setResultSet()
			setResultSet(con.createStatement().executeQuery(getSql()));
			// si hay resultados, los guardas en el buffer y sigues para en el doAfterBody()
			// iterar por todo el listado
			// si no hay, saltas todo el body y vas directo a doEndTag()
			if (getResultSet().next())
				return EVAL_BODY_BUFFERED; // guarda el primer elemento del resultSet en buffer y sigue a doAfterBody()
			else
				return SKIP_BODY;
		} catch (ClassNotFoundException | SQLException e) {
			return SKIP_PAGE;
		}

	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			if (getResultSet().next())
				return EVAL_BODY_AGAIN;// como hay más resultados vuelve al body(startTag y repite. no habre nueva
										// conexion pq esa ya está creada en la ConexionBodyTag padre
			else
				return SKIP_BODY;
		} catch (SQLException e) {
			// si hay problema, igualmente hay que cerrar la conexion:
			// coges la instancia de la clase que tiene la conexion

			try {
				ConexionBodyTag padre = (ConexionBodyTag) findAncestorWithClass(this,
						Class.forName("curso.cap.tagsPractice.tags.ConexionBodyTag"));
				// coges de esa instancia la conexion y la cierras
				padre.getConexion().close();
			} catch (ClassNotFoundException | SQLException e1) {

			}
			return SKIP_PAGE;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		/*
		 * el serviddor de aplicaciones ha ido guardando en un buffer todo lo que se
		 * encuentra en el html (que está programado en html o en etiquetas creadas) el
		 * doEndTag() coge todo lo que hay en el buffer y con un writer() se lo manda al
		 * cliente
		 */
		// ahora hay que escribir todo lo del buffer del body de la tag a un Writer para
		// luego poder usarlo en el response
		// previousOut es el jspWriter
		try {
			getBodyContent().writeOut(getPreviousOut());
			return EVAL_PAGE;
		} catch (IOException e) {
			// y hay que cerrar la conexion
			ConexionBodyTag padre;
			try {
				padre = (ConexionBodyTag) findAncestorWithClass(this,
						Class.forName("curso.cap.tagsPractice.tags.ConexionBodyTag"));
				// coges de esa instancia la conexion y la cierras
				padre.getConexion().close();
			} catch (ClassNotFoundException | SQLException e1) {

			}
			return SKIP_PAGE;

		}
	}

}
