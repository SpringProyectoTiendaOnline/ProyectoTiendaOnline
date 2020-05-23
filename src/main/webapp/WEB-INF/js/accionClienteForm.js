$(document).ready(function() {
	$("#registrarCliente").submit(function() {
		event.preventDefault();
		registrarCliente();
	});

	$("#modificarCliente").submit(function() {
		event.preventDefault();
		modificarCliente();
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
	var rol = $('#rol').val();

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
					console.log(rol);
					if (rol == "admin") {
						// var url =
						// "http://localhost:8080/ProyectoTiendaOnline/login";
						// window.open(url);
						$('#aviso').html("");
						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Cliente/list/1";

					} else {
						// var url =
						// "http://localhost:8080/ProyectoTiendaOnline/login";
						// window.open(url);
						$('#aviso').html("");
						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/login";
					}

				},// success

				error : function(xhr, status, error) {
					var aviso = "";
					$('#aviso').html(aviso);

					$('#nombre').val(nombre);
					$('#apellido').val(apellido);
					$('#fnacimiento').val(fnacimiento);
					$('#email').val(email);
					$('#direccion').val(direccion);
					$('#password').val(password);

					aviso = "<div  class='alert alert-danger'> El Correo Electronico ya existe en nuestra web"
							+ "</div>";
					$('#aviso').html(aviso);

				}

			});
}

function modificarCliente() {
	// get the form values
	var rol = $('#rol').val();
	var nombre = $('#nombre').val();
	var apellido = $('#apellido').val();
	var fnacimiento = $('#fnacimiento').val();
	var email = $('#email').val();
	var direccion = $('#direccion').val();
	var password = $('#password').val();
	var idCliente = $('#idCliente').val();

	var cliente = ({
		'idCliente' : idCliente,
		'nombre' : nombre,
		'apellido' : apellido,
		'fnacimiento' : fnacimiento,
		'email' : email,
		'direccion' : direccion,
		'password' : password,
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
				url : "http://localhost:8080/ProyectoTiendaOnline/Cliente/editar-cliente/"
						+ idCliente,
				type : 'POST',
				contentType : "application/json; charset=utf-8",
				data : clienteData,
				cache : false,
				success : function(data) {
					var aviso = "";
					$('#aviso').html("");

					if (rol == "admin") {
						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Cliente/list/1";
					} else {
						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Cliente/perfil-cliente/"
								+ idCliente;
					}

					$('#aviso').html("");
					console.log("Modificar Cliente :  OK");

					aviso = "<div class='alert alert-success'> Los datos del Cliente han sido modificados correctamente";
					+"</div>";
					$('#aviso').html(aviso);

				},// success

				error : function(xhr, status, error) {
					var aviso = "";
					$('#aviso').html(aviso);
					$('#nombre').val(nombre);
					$('#apellido').val(apellido);
					$('#fnacimiento').val(fnacimiento);
					$('#email').val(email);
					$('#direccion').val(direccion);
					$('#password').val(password);

					aviso = "<div class='alert alert-danger'> El Correo Electronico ya existe en nuestra web"
							+ "</div>";
					$('#aviso').html(aviso);
				}

			});

}