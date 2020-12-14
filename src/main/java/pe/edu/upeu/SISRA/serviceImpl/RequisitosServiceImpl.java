package pe.edu.upeu.SISRA.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.upeu.SISRA.dao.RequisitosDao;
import pe.edu.upeu.SISRA.service.DocumentoService;
import pe.edu.upeu.SISRA.service.RequisitosService;

@Service
public class RequisitosServiceImpl implements RequisitosService {

	@Autowired
	private RequisitosDao rDao;

	@Override
	public Map<String, Object> listarRequisitos(String usuario) {
		return rDao.listarRequisitos(usuario);
	}

	@Override
	public Map<String, Object> EliminarRequisito(String eliminar, String archivo) {
		return rDao.EliminarRequisito(eliminar, archivo);
	}

	@Override
	public Map<String, Object> SubirRequisito(MultipartFile archivo, String usuario) {
		return rDao.CrearRequisito(archivo, usuario);
	}

	public void ActualizarRequisito(MultipartFile archivo, String usuario) {
		// Funcion inutil, confusion mia disculpen :
	}
}
