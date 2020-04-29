 $(document).ready(function() {
	$( "#enviarPregunta" ).click(function() {
		enviarPregunta();
	});
	
	$( "#buttonEnviarRespuesta" ).click(function() {
		addRespuestas();
	});

});
 
function enviarPregunta(){
	var idProducto = document.getElementById('idProducto').value;
	var textoPregunta = document.getElementById('textoPregunta').value;
	
	// token de html
	
	var token = $("meta[name='_csrf']").attr("content");
	  var header = $("meta[name='_csrf_header']").attr("content");
	  $(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	  });
	
	 var pregunta = {'textoPregunta':$(':input[name=textoPregunta]').val(),
				'idProducto':$(':input[name=idProducto]').val(),
				'idCliente':$(':input[name=idUsuario]').val()
	};
	//console.log(textoPregunta + " " + pregunta);
	$.ajax({
		url : "http://localhost:8080/ProyectoTiendaOnline/Producto/enviarPregunta/"+idProducto,
		type : 'GET',
		contentType : "application/json; charset=utf-8",
		data : "&textoPregunta;=" + $("#textoPregunta").val()+ "&idProducto;=" + $("#idProducto").val() + "&idCliente;=" + $("idCliente").val(),
		cache : false,
		success : function(response){
	         var obj = JSON.parse(response);
	        var lista = "<p>Pregunta : <span>"+textoPregunta+"</span>"+"</p>"+
						  "<p> By : <span>" +idCliente + "</span></p>"
						  "<button class='btn btn-outline-info'>"+ "Ver la respuesta "+"</button>"+
						  "<hr />";
	         $("#listaPregunta").append(lista);
	        
		},
		error : function(xhr, status, error) {
			console.log("error de enviar la pregunta");
		}
	});
};







function addRespuestas(){
	var idPregunta = document.getElementById('idPregunta').value;
	
	var respuesta = {'textoRespuesta':$(':input[name=textoRespuesta]').val(),
				'idPregunta':$(':input[name=idPregunta]').val(),
				'idCliente':$(':input[name=idUsuario]').val()
	};
	
	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});//ajaxSend

	
	var respuestaData = JSON.stringify(respuesta);
	
	console.log(idPregunta + " " + respuestaData);
	
	$.ajax({
		url : "http://localhost:8080/ProyectoTiendaOnline/Producto/enviar-respuesta/"+idPregunta,
		type : "POST",
		cache : false,
		dataType : "json",
		data : respuestaData,
		contentType : "application/json; charset=utf-8",
		
		success : function(response){
			console.log(response);
		},
		
		error : function(xhr, status, error) {
			console.log(xhr + " , " + status + ", " + error);
		},
		
		
	});
	
};

