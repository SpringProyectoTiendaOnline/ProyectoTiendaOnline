$(document).ready(function() {
	var boton = document.getElementsByClassName('borrarProductoCategoria');
	for (var i = 0; i < boton.length; i++) {
		boton[i].addEventListener('click', eliminarProductoCategoria, false);
	}

	$("#changeInputButton").click(function() {
		changeTextoToInput();
	});
	
	$('#buttonCancelarModifNomCategoria').click(function() {
		cancelarChangeNombreCategoria();
	});
	
	$('#modificarNombreCategoria').click(function() {
		modificarNombreCategoria();
	});
		
	
});

// Crear typeahead
var producto = new Bloodhound(
		{
			datumTokenizer : Bloodhound.tokenizers.obj.whitespace('name'),
			queryTokenizer : Bloodhound.tokenizers.whitespace,
			remote : {
				url : "http://localhost:8080/ProyectoTiendaOnline/Producto/searchProducto/%QUERY",
				wildcard : '%QUERY'
			}
		});

$('.typeahead').typeahead({
	minLength : 1,
	highlight : true
}, {
	name : 'producto',
	display : 'titulo',
	source : producto
}).on("typeahead:select", function(e, producto) {
	addProductoCategoria(producto);
});

function addProductoCategoria(productoDto) {
	var idCategoria = document.getElementById('idCategoria').value;

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Categoria/insertarProducto/"
						+ idCategoria,
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(productoDto),
				type : "POST",
				success : function(response) {
					var fila = "<tr sec:authorize='hasAuthority('ROL_ADMIN')'>"
							+ "<td style='display:none;' id='idProducto'>"
							+ productoDto.idProducto
							+ "</td>"
							+ "<td>"
							+ productoDto.codProducto
							+ "</td>"
							+ "<td>"
							+ productoDto.titulo
							+ "</td>"
							+ "<td><a class='btn btn-danger borrarProductoCategoria'><i>&times;</i></a></td>"
							+ "</tr>"

					$('#tablaProductosCategoria').append(fila);

					var botonEliminar = document
							.getElementsByClassName('borrarProductoCategoria');
					for (var i = 0; i < botonEliminar.length; i++) {
						botonEliminar[i].addEventListener('click',
								eliminarProductoCategoria, false);
					}

					$('#aviso').html("");

				},// success

				// Si en caso hay error.
				error : function(xhr, status, error) {

					var aviso = "<div class='alert alert-danger' role='alert'>"
							+ "El producto ya imparte en esta categoria"
							+ "</div>";

					$('#aviso').html(aviso);
				}// error
			});// ajax

};// add Producto

// Borrar los productos dentro de la categoria
function eliminarProductoCategoria() {
	var obj = $(this);
	var idProducto = $(this).closest("tr").find("#idProducto").text();// si
	// encuentra,
	// se
	// cierra
	// "tr"
	var idCategoria = document.getElementById("idCategoria").value;

	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Categoria/eliminarProducto/"
						+ idCategoria + "/" + idProducto,
				contentType : "application/json; charset=utf-8",
				method : "DELETE",
				success : function(response) {
					$(obj).closest("tr").remove(); // eliminar el elemento de
					// obj dentro del tr de
					// producyo

				},

				error : function(xhr, status, error) {
					var aviso = "<div class='alert alert-danger' role='alert'>"
							+ "El producto ya no imparte este categoria"
							+ "</div>" + "";
					$('#aviso').html(aviso);
				}
			});

};


function changeTextoToInput() {
	var nombreCategoria = $('#nombreCategoria').text();
	
	var input = "<input type='text' id='nombreCategoria' class='form-control col-md-8' value= "+nombreCategoria+ "> &nbsp;"
			+ " <button type='submit' class='btn btn-primary mb-2 modificarNombreCategoria' id='modificarNombreCategoria'><i class='fas fa-edit'></i></button> &nbsp; &nbsp;"
			+ " <button class='btn btn-primary mb-2 buttonCancelarModifNomCategoria' id='buttonCancelarModifNomCategoria'>Cancelar</button>";
	
	
	$('#modificarNombreCategoria').click(function() {
		modificarNombreCategoria();
	});

	$("#buttonCancelarModifNomCategoria").click(function() {
		cancelarChangeNombreCategoria();
	});
	
	$('#funcionEditarCategoria').html(input);



}

function cancelarChangeNombreCategoria() {
	var nombreCategoria = $('#nombreCategoria').val();
	var input = "<span>Categoría : </span> <span id='nombreCategoria' name='nombreCategoria'>" + nombreCategoria +  "</span> &nbsp; <button class='btn btn-primary changeInputButton' id = 'changeInputButton'> <i class='fas fa-edit'></i></button>";
	console.log(nombreCategoria);
	$('#funcionEditarCategoria').html(input);

	$("#changeInputButton").click(function() {
		changeTextoToInput();
	});

}

function modificarNombreCategoria(){
	
	var nombreCategoria = $('#nombreCategoria').val();
	console.log(nombreCategoria);
	
	$("#buttonCancelarModifNomCategoria").click(function() {
		cancelarChangeNombreCategoria();
	});
	
}
