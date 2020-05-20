$(document).ready(function() {
	$("#createProducto").submit(function() {
		event.preventDefault();

		if (validaForm()) {
			createProducto();
		}

	});

	$("#modificarProducto").submit(function() {
		event.preventDefault();
		if (validaForm()) {
			modificarProducto();
		}
	});

	var botonEliminar = document.getElementsByClassName('borrarProducto');
	for (var i = 0; i < botonEliminar.length; i++) {
		botonEliminar[i].addEventListener('click', eliminarProducto, false);
	}

});

function validaForm() {
	var aviso = "";
	// Campos de texto
	if ($("#titulo").val() == "") {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo titulo no puede ser vacio" + "</div>";

		$("#titulo").focus(); // Esta función coloca el foco de escritura del
		// usuario en el campo Nombre directamente.
	}

	if ($("#titulo").val().length < 5) {
		aviso = "<div class='alert alert-danger'>"
				+ "El campo titulo, su caracter no puede ser menor que 5 "
				+ "</div>";
		$("#titulo").focus(); // Esta función coloca el foco de escritura del
		// usuario en el campo Nombre directamente.
	}

	if ($("#descripcion").val() == "") {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo descripcion no puede ser vacio" + "</div>";
		$("#descripcion").focus();
	}

	if ($("#descripcion").val().length < 5) {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo descripcion, su caracter no puede ser menor que 5 "
				+ "</div>";
		$("#descripcion").focus(); // Esta función coloca el foco de escritura
		// del usuario en el campo Nombre
		// directamente.
	}

	if ($("#precio").val() < 0.01) {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo precio tiene que ser mayor que 0.01" + "</div>";
		$("#precio").focus(); // Esta función coloca el foco de escritura
		// del usuario en el campo Nombre
		// directamente.
	}

	if ($("#codProducto").val() == 0) {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo codigo de producto no puede ser 0" + "</div>";
		$("#codProducto").focus(); // Esta función coloca el foco de escritura
		// del usuario en el campo Nombre
		// directamente.
	}

	if ($("#stock").val() == 0) {
		aviso += "<div class='alert alert-danger'>"
				+ "El campo stock no puede ser 0" + "</div>";
		$("#stock").focus(); // Esta función coloca el foco de escritura del
		// usuario en el campo Nombre directamente.
	}

	if (aviso != "") {
		console.log(aviso);

		$('#aviso').html(aviso);
		return false;

	}

	return true; // Si todo está correcto
}

function createProducto() {
	// get the form values
	var titulo = $('#titulo').val();
	var descripcion = $('#descripcion').val();
	var codProducto = $('#codProducto').val();
	var precio = $('#precio').val();
	var descuento = $('#descuento').val();
	var stock = $('#stock').val();

	var producto = ({
		'titulo' : titulo,
		'descripcion' : descripcion,
		'codProducto' : codProducto,
		'precio' : precio,
		'descuento' : descuento,
		'stock' : stock
	});

	var productoData = JSON.stringify(producto);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/create-producto",
				type : 'POST',
				contentType : "application/json; charset=utf-8",
				data : productoData,
				cache : false,

				success : function(data) {

					$('#aviso').html("");

					console.log("OKKK");

					window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Producto/list-producto";

				},// success

				error : function(xhr, status, error) {

					var aviso = "";
					$('#aviso').html(aviso);

					$('#titulo').val(titulo);
					$('#descripcion').val(descripcion);
					$('#codProducto').val(codProducto);
					$('#precio').val(precio);
					$('#descuento').val(descuento);
					$('#stock').val(stock);

					aviso = "<div class='alert alert-danger'>"
							+ "Ya Existe el código de producto." + "</div>";
					$('#aviso').html(aviso);

				}

			});
};

function modificarProducto() {
	// get the form values
	var idProducto = $('#idProducto').val();
	var titulo = $('#titulo').val();
	var descripcion = $('#descripcion').val();
	var codProducto = $('#codProducto').val();
	var precio = $('#precio').val();
	var descuento = $('#descuento').val();
	var stock = $('#stock').val();

	var producto = ({
		'idProducto' : idProducto,
		'titulo' : titulo,
		'descripcion' : descripcion,
		'codProducto' : codProducto,
		'precio' : precio,
		'descuento' : descuento,
		'stock' : stock
	});

	var productoData = JSON.stringify(producto);

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/editar-producto/"
						+ idProducto,
				type : 'POST',
				contentType : "application/json; charset=utf-8",
				data : productoData,
				cache : false,
				success : function(data) {
					$('#aviso').html("");
					window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Producto/list-producto";

				},// success

				error : function(xhr, status, error) {

					var aviso = "";
					$('#aviso').html(aviso);

					$('#titulo').val(titulo);
					$('#descripcion').val(descripcion);
					$('#codProducto').val(codProducto);
					$('#precio').val(precio);
					$('#descuento').val(descuento);
					$('#stock').val(stock);

					aviso = "<div class='alert alert-danger'>"
							+ "Ya Existe el código de producto." + "</div>";
					$('#aviso').html(aviso);

				}// error

			});// ajax
};

function eliminarProducto() {

	var obj = $(this);
	var idProducto = $(this).closest("tr").find("#idProducto").text();// si
	// encuentra,
	// se
	// cierra
	// "tr"

	console.log(idProducto);
	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/removeproducto/"
						+ idProducto,
				contentType : "application/json; charset=utf-8",
				method : "DELETE",
				success : function(response) {

					$(obj).closest("tr").remove(); // eliminar el elemento de
					// obj dentro del tr de
					// producyo
					var aviso = "<div class='alert alert-success' role='alert'>"
							+ "El producto ha sido eliminado correctamente !"
							+ "</div>" + "";
					$('#aviso').html(aviso);

				},

				error : function(xhr, status, error) {
					var aviso = "<div class='alert alert-danger' role='alert'>"
							+ "El producto ya no existe" + "</div>" + "";
					$('#aviso').html(aviso);
				}
			});

}