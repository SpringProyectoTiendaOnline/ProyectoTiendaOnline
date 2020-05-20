$(document).ready(function() {
	$("#buttonEnviarRespuesta").submit(function() {
		event.preventDefault();
		addRespuestas();
	});

});

function addRespuestas() {
	var idPregunta = document.getElementById('idPregunta').value;
	var textoRespuesta = document.getElementById('textoRespuesta').value;

	var respuesta = {
		'texto' : $(':input[name=textoRespuesta]').val(),
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
				data : respuestaData,
				contentType : "application/json; charset=utf-8",

				success : function(response) {
					console.log(response);
					var text = "<p>" + textoRespuesta + "</p><hr />";
					$("#listaRespuesta").append(text);
					$('#textoRespuesta').val("");

				},

				error : function(xhr, status, error) {
					console.log(xhr + " , " + status + ", " + error);
				},

			});

};
