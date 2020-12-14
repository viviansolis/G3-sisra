
package pe.edu.upeu.SISRA.dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface RequisitosDao {

	public Map<String, Object> listarRequisitos(String usuario);

	public Map<String, Object> EliminarRequisito(String eliminar, String archivo);

	public Map<String, Object> ActualizarRequisito(MultipartFile archivo, String usuario);

	public Map<String, Object> CrearRequisito(MultipartFile archivo, String usuario);
}
