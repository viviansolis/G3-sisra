package pe.edu.upeu.SISRA.daoImpl;

import java.sql.Types;
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

import oracle.jdbc.internal.OracleTypes;

import pe.edu.upeu.SISRA.dao.DocumentoDao;
import pe.edu.upeu.SISRA.entity.Documento;

@Component
public class DocumentoDaoImpl implements DocumentoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Override
	public Map<String, Object> read(int id_req_asc) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		.withCatalogName("pk_documento")
		.withProcedureName("sp_req_asc")
		.declareParameters(new SqlOutParameter("cursor_req_asc", Types.REF_CURSOR, new ColumnMapRowMapper()), new SqlParameter("id_asc", Types.NUMERIC));
		SqlParameterSource in = new MapSqlParameterSource().addValue("id_asc", id_req_asc);
		Map<String, Object> map = simpleJdbcCall.execute(in);	
		return map;
	}
	@Override
	public Map<String, Object> listar_req(int id) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)			
				.withCatalogName("pk_documento") //nombre del paquete
				.withProcedureName("sp_listar_req") //nombre del procedimiento
				.declareParameters(new SqlOutParameter("cursor_req", OracleTypes.REF_CURSOR, new ColumnMapRowMapper()), new SqlParameter("id", OracleTypes.NUMBER));
				SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
		        Map<String, Object> map= simpleJdbcCall.execute(in);	
				return map;
	}

	@Override
	public Map<String, Object> historial_doc(int id_asc, int id_doc) {
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)			
				.withCatalogName("pk_documento") //nombre del paquete
				.withProcedureName("sp_his_doc") //nombre del procedimiento
				.declareParameters(new SqlOutParameter("cursor_req", OracleTypes.REF_CURSOR, new ColumnMapRowMapper()), new SqlParameter("id_asc", OracleTypes.NUMBER), new SqlParameter("id_doc", OracleTypes.NUMBER));
				SqlParameterSource in = new MapSqlParameterSource().addValue("id_asc", id_asc).addValue("id_doc", id_doc);
		        Map<String, Object> map= simpleJdbcCall.execute(in);	
				return map;	
	}
	@Override
	public int create(Documento r) {
		// TODO Auto-generated method stub
		return  jdbcTemplate.update("call pk_documento.sp_create_doc(?,?,?,?,?,?,?,?)", r.getF_registro(),r.getUrl(),r.getEstado(),
				r.getCodigo(),
				r.getTipo_documento_id_doc_tipo(),
			r.getAsociacion_id_asoc(),
			r.getId_ult_rev(),
			r.getCategoria());
	}

}
