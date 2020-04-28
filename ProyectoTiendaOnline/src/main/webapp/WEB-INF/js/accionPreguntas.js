$(document).ready(function() {
	$("#enviarPregunta").click(function() {
		addPreguntas();
	});

	$("#buttonEnviarRespuesta").click(function() {
		addRespuestas();
	});

});

function addPreguntas() {
	var idProducto = document.getElementById('idProducto').value;
	var textoPregunta = document.getElementById('textoPregunta').value;

	var pregunta = {
		'texto' : $(':input[name=textoPregunta]').val()
	};
	// 'idProducto':$(':input[name=idProducto]').val(),
	// 'idCliente':$(':input[name=idUsuario]').val()

	var preguntaData = JSON.stringify(pregunta);

	var preg = JSON.parse(preguntaData);

	console.log(textoPregunta + " " + pregunta + " " + preguntaData + " "
			+ preg);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/enviarPregunta/"
						+ idProducto,
				type : 'post',
				contentType : "application/json; charset=utf-8",
				data : textoPregunta,
				cache : false,
				dataType : "text",
				success : function(response) {
					var lista = "<p>Pregunta : <span>" + textoPregunta
							+ "</span>" + "</p>" + "<p> By : <span>"
							+ "</span></p>"+"<button class='btn btn-outline-info'> Ver la respuesta </button>" + "<hr />";
					$("#listaPregunta").append(lista);
				},//success

				error : function(xhr, status, error) {
					console.log("error de enviar la pregunta");
				}

			});

};

function addRespuestas() {
	var idPregunta = document.getElementById('idPregunta').value;

	var respuesta = {
		'textoRespuesta' : $(':input[name=textoRespuesta]').val(),
		'idPregunta' : $(':input[name=idPregunta]').val(),
		'idCliente' : $(':input[name=idUsuario]').val()
	};

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	var respuestaData = JSON.stringify(respuesta);

	console.log(idPregunta + " " + respuestaData);

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/enviar-respuesta/"
						+ idPregunta,
				type : "POST",
				cache : false,
				dataType : "json",
				data : respuestaData,
				contentType : "application/json; charset=utf-8",

				success : function(response) {
					console.log(response);
				},

				error : function(xhr, status, error) {
					console.log(xhr + " , " + status + ", " + error);
				},

			});

};

