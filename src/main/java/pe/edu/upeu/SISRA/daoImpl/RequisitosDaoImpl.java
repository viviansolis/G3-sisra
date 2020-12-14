package pe.edu.upeu.SISRA.daoImpl;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.SISRA.dao.RequisitosDao;
import pe.edu.upeu.SISRA.utilidades.Archivos;

@Component
public class RequisitosDaoImpl implements RequisitosDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;

	@Override
	public Map<String, Object> listarRequisitos(String id_usuario) {

		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pk_requisitos")
				.withProcedureName("listar_requisitos")
				.declareParameters(new SqlOutParameter("cursorurs", Types.REF_CURSOR, new ColumnMapRowMapper()),
						new SqlParameter("id_user", Types.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource();
		in.addValue("id_user", id_usuario);

		Map<String, Object> map = simpleJdbcCall.execute(in);
		return map;
	}

	@Override
	public Map<String, Object> EliminarRequisito(String global_Id, String archivo) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pk_requisitos")
					.withProcedureName("eliminar").declareParameters(new SqlParameter("identificacion", Types.NUMERIC));

			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("identificacion", global_Id);
			Archivos.EliminarArchivo(archivo);
			simpleJdbcCall.execute(in);
			result.put("resultado", "normal");

			return result;
		} catch (Exception e) {
			System.out.print("Error: " + e);
			result.put("resultado", "error");
			return result;
		}
	}

	@Override
	public Map<String, Object> CrearRequisito(MultipartFile archivo, String us) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String url = Archivos.almacenarArchivo(archivo);

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pk_requisitos")
					.withProcedureName("anexar").declareParameters(new SqlParameter("usuario", Types.VARCHAR),
							new SqlParameter("urlp", Types.VARCHAR));

			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("urlp", url);
			in.addValue("usuario", us);

			simpleJdbcCall.execute(in);

			result.put("resultado", "normal");

			return result;
		} catch (Exception e) {
			System.out.print("Error: " + e);
			result.put("resultado", "error");

			return result;
		}

	}

	@Override
	public Map<String, Object> ActualizarRequisito(MultipartFile archivo, String global_id) {
		return null;
	}
}
