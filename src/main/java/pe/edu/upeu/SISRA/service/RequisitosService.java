package pe.edu.upeu.SISRA.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface RequisitosService {

	public Map<String, Object> listarRequisitos(String usuario);

	public Map<String, Object> EliminarRequisito(String eliminar, String archivo);

	public Map<String, Object> SubirRequisito(MultipartFile archivo, String usuario);

	public void ActualizarRequisito(MultipartFile archivo, String globalID);
}
