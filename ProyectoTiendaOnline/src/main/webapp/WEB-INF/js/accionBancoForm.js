$(document).ready(function() {
	$("#numTarjeta").change(function() {
		ValidarTJ();
	});

	$("#addBanco").submit(function() {
		event.preventDefault();
		registrarBanco();
	});

	$("#modificarBanco").submit(function() {
		event.preventDefault();
		modificarBanco();
	});

});

function ValidarTJ() {
	// El primer número identifica a la tarjeta:
	// si es un 3 la tarjeta es American Express
	// si es un 4 la tarjeta es Visa
	// si es un 5 la tarjeta es MasterCard
	// si es un 6 la tarjeta es Discover

	$('#errorNumBanco').html("");

	var numero_tarjeta = document.getElementById('numTarjeta').value;

	console.log(numero_tarjeta);

	var mensaje = "";
	var cadena = numero_tarjeta.toString();
	var longitud = cadena.length;
	var cifra = null;
	var cifra_cad = null;
	var suma = 0;

	if (cadena == "0" || cadena == "") {
		var mensaje = "<div class='alert alert-alert'>"
				+ "El número de tarjeta no puede ser 0 o vacío" + "</div>";
		$('#errorNumBanco').html(mensaje);
		return false;

	} else {
		for (var i = 0; i < longitud; i += 2) {
			cifra = parseInt(cadena.charAt(i)) * 2;
			if (cifra > 9) {
				cifra_cad = cifra.toString();
				cifra = parseInt(cifra_cad.charAt(0))
						+ parseInt(cifra_cad.charAt(1));
			}
			suma += cifra;
		}

		for (var i = 1; i < longitud; i += 2) {
			suma += parseInt(cadena.charAt(i));
		}

		if ((suma % 10) == 0) {
			var mensaje = "<div class='alert alert-success'>"
					+ "El número de tarjeta es válido" + "</div>";
			$('#errorNumBanco').html(mensaje);
			return true;

		} else {
			var mensaje = "<div class='alert alert-danger'>"
					+ "El número de tarjeta no es válido" + "</div>";
			$('#errorNumBanco').html(mensaje);
			return false;
		}
	}

};

function modificarBanco() {
	// get the form values
	var idBanco = $('#idBanco').val();
	var idCliente = $('#idCliente').val();
	var nombre = $('#nombre').val();
	var numTarjeta = $('#numTarjeta').val();
	var titular = $('#titular').val();
	var codSeguridad = $('#codSeguridad').val();
	var dirFactura = $('#dirFactura').val();

	var banco = ({
		'idBanco' : idBanco,
		'nombre' : nombre,
		'numTarjeta' : numTarjeta,
		'titular' : titular,
		'codSeguridad' : codSeguridad,
		'dirFactura' : dirFactura,
	});

	var bancoData = JSON.stringify(banco);
	console.log(bancoData);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	if (ValidarTJ() == true) {
		console.log("valido");
		$
				.ajax({
					url : "http://localhost:8080/ProyectoTiendaOnline/Banco/editar-banco/"
							+ idCliente + "/" + idBanco,
					type : 'POST',
					contentType : "application/json; charset=utf-8",
					data : bancoData,
					cache : false,
					success : function(data) {
						// var url =
						// "http://localhost:8080/ProyectoTiendaOnline/login";
						// window.open(url);
						$('#aviso').html("");

						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Cliente/perfil-cliente/"
								+ idCliente;

					},// success

					error : function(xhr, status, error) {
						var aviso = "";
						$('#nombre').val(nombre);
						$('#numTarjeta').val(numTarjeta);
						$('#titular').val(titular);
						$('#codSeguridad').val(codSeguridad);
						$('#dirFactura').val(dirFactura);
						aviso = "<div class='alert alert-danger'> El numero de tarjeta ya está asignada";
						+"</div>";
						$('#aviso').html(aviso);
					}

				});

	}

};

function registrarBanco() {
	// get the form values
	var nombre = $('#nombre').val();
	var numTarjeta = $('#numTarjeta').val();
	var titular = $('#titular').val();
	var codSeguridad = $('#codSeguridad').val();
	var dirFactura = $('#dirFactura').val();

	var idCliente = document.getElementById('idCliente').value;

	var banco = ({
		'nombre' : nombre,
		'numTarjeta' : numTarjeta,
		'titular' : titular,
		'codSeguridad' : codSeguridad,
		'dirFactura' : dirFactura,
		'idCliente' : idCliente,
	});

	var bancoData = JSON.stringify(banco);
	console.log(bancoData);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	if (ValidarTJ() == true) {
		console.log("valido");
		$
				.ajax({
					url : "http://localhost:8080/ProyectoTiendaOnline/Banco/create-banco/"
							+ idCliente,
					type : 'POST',
					contentType : "application/json; charset=utf-8",
					data : bancoData,
					cache : false,
					success : function(data) {
						// var url =
						// "http://localhost:8080/ProyectoTiendaOnline/login";
						// window.open(url);
						$('#aviso').html("");

						window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Cliente/perfil-cliente/"
								+ idCliente;

					},// success

					error : function(xhr, status, error) {
						var aviso = "";
						$('#aviso').html(aviso);
						$('#nombre').val('');
						$('#numTarjeta').val('');
						$('#titular').val('');
						$('#codSeguridad').val('');
						$('#dirFactura').val('');

						$('#aviso').html(aviso);
					}

				});

	}
};