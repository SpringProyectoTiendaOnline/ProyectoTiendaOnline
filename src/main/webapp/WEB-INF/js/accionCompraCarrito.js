$(document).ready(function() {
	$("#addCarrito").click(function() {
		console.log("Entrado");
		event.preventDefault();
		addToCart();

	});

});

function addToCart() {

	var idProducto = document.getElementById('idProducto').value;
	var idCliente = document.getElementById('idUsuario').value;

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	console.log("id : " + idProducto + " " + idCliente);

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Compra/comprarProducto/"
						+ idCliente + "/" + idProducto,
				contentType : "application/json; charset=utf-8",
				type : "POST",
				success : function(response) {
					$('#avisoCarrito').html("");

					aviso = "<div class='alert alert-success'>"
							+ "Añadido correctamente." + "</div>";
					$('#avisoCarrito').html(aviso);

				},// success

				// Si en caso hay error.
				error : function(xhr, status, error) {

					console.log("Error de añadir a carrito")
				}// error
			});// ajax

};// add cart
