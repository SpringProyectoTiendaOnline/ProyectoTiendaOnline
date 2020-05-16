$(document).ready(function() {
	$("#registrarCliente").click(function() {
		registrarCliente();
	});
});

function registrarCliente() {
	// get the form values
	var nombre = $('#nombre').val();
	var apellido = $('#apellido').val();
	var fnacimiento = $('#fnacimiento').val();
	var email = $('#email').val();
	var direccion = $('#direccion').val();
	var password = $('#password').val();

	var cliente = ({
		'nombre' : nombre,
		'apellido' : apellido,
		'fnacimiento' : fnacimiento,
		'email' : email,
		'direccion' : direccion,
		'password' : password
	});

	var clienteData = JSON.stringify(cliente);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Cliente/signup",
				type : 'POST',
				contentType : "application/json; charset=utf-8",
				data : clienteData,
				cache : false,
				success : function(data) {
					// var url =
					// "http://localhost:8080/ProyectoTiendaOnline/login";
					// window.open(url);
					$('#aviso').html("");

					window.location.href = "http://localhost:8080/ProyectoTiendaOnline/login";

				},// success

				error : function(xhr, status, error) {
					var aviso = "";
					$('#aviso').html(aviso);

					$('#nombre').val('');
					$('#apellido').val('');
					$('#fnacimiento').val('');
					$('#email').val('');
					$('#direccion').val('');
					$('#password').val('');
					aviso = "<div class='alert alert-danger'> El Correo Electronico ya existe en nuestraweb"
							+ "</div>";
					$('#aviso').html(aviso);
				}

			});
}