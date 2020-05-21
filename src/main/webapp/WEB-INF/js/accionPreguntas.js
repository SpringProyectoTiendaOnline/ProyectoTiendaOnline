$(document).ready(function() {
	$("#enviarPregunta").submit(function() {
		event.preventDefault();
		addPreguntas();
	});

	/*$("#showRespuesta").click(function() {
		var idPregunta = document.getElementById('idPregunta').value;
		openRespuesta(idPregunta);
	});*/

});

function addPreguntas() {
	var idProducto = document.getElementById('idProducto').value;

	var textoPregunta = document.getElementById('textoPregunta').value;

	var pregunta = {
		'texto' : $(':input[name=textoPregunta]').val(),
		'idProducto' : $(':input[name=idProducto]').val(),
		'idCliente' : $(':input[name=idUsuario]').val()
	};

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	var preguntaData = JSON.stringify(pregunta);

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/enviarPregunta/"
						+ idProducto,
				type : 'POST',
				contentType : "application/json; charset=utf-8",
				data : preguntaData,
				cache : false,

				success : function(data) {
					console.log(data.idPregunta);

					var lista = "<p id='idPregunta' style='display: none;'>"
							+ data.idPregunta
							+ "</p>"
							+ "<p>Pregunta : <span>"
							+ textoPregunta
							+ "</span>"
							+ "</p>"
							+ "<a class='btn btn-outline-info showRespuesta' id='showRespuesta' href='http://localhost:8080/ProyectoTiendaOnline/Producto/respuestaProducto/"
							+ data.idPregunta + "'>Ver la respuesta</a>"
							+ "<hr />";

					$("#listaPregunta").append(lista);
					$('#textoPregunta').val("");

				},// success

				error : function(xhr, status, error) {
					console.log("error de enviar la pregunta");
				}

			});

};
