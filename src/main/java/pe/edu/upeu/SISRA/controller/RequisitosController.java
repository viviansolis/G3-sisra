package pe.edu.upeu.SISRA.controller;

import java.sql.SQLType;
import java.sql.Types;
import java.util.Map;
import java.util.Random;

import pe.edu.upeu.SISRA.service.RequisitosService;
import pe.edu.upeu.SISRA.utilidades.Archivos;

import javax.annotation.Resource;
import javax.management.ServiceNotFoundException;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.websocket.server.PathParam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/requisitos")
@RestController
public class RequisitosController {
	@Autowired
	public RequisitosService requisitoService;

	@RequestMapping(value = "/listar", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Object> listarRequisitos(@RequestParam("usuario") String id_usuario) {
		return requisitoService.listarRequisitos(id_usuario);
	}

	@RequestMapping(value = "/anexar", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Object> crearRequisito(@RequestParam("usuario") String us,
			@RequestParam("archivo") MultipartFile archivo) {
		return requisitoService.SubirRequisito(archivo, us);
	}

	@RequestMapping(value = "/eliminar", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Object> eliminarRequisito(@RequestParam("globalid") String globalID,
			@RequestParam("archivo") String archivo) {
		return requisitoService.EliminarRequisito(globalID, archivo);
	}

	@GetMapping("/pdf")
	public void descargarDocumento(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("doc") String doc) {
		// If user is not authorized - he should be thrown out from here itself

		// Authorized user will download the file
		String dataDirectory = (System.getProperty("user.dir") + "/archivos");
		Path file = Paths.get(dataDirectory, doc);
		if (Files.exists(file)) {
			response.setContentType("application/pdf");
			try {
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.print("Descargar : " + file);
		}
	}
}