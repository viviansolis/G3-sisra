package pe.edu.upeu.SISRA.dao;

import java.util.Map;

import pe.edu.upeu.SISRA.entity.Documento;



public interface DocumentoDao {
	int create(Documento r);
	Map<String, Object> listar_req(int id);
	Map<String, Object> historial_doc(int id_asc, int id_doc);
	Map<String, Object> read(int id_req_asc);
}
