//Crear typeahead
var producto = new Bloodhound(
		{
			datumTokenizer : Bloodhound.tokenizers.obj.whitespace,
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
	display : 'nombreProducto',
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
	});

	$
			.ajax({
				url : "http://localhost:8080/ProyectoTiendaOnline/Categoria/insertarProducto/"
						+ idCategoria,
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(productoDto),
				type : "POST",
				success : function(response) {
					var fila = "<tr>"
							+ "<td style='display:none;' id='idProducto'>"
							+ productoDto.idProducto + "</td>" + "<td>"
							+ productoDto.codProducto + "</td>" + "<td>"
							+ productoDto.nombreProducto + "</td>" + "</tr>";

					$('#tablaProductosCategoria').append(fila);

				},
				// Si en caso hay error.
				error : function(xhr, status, error) {

					var aviso = "<div class='alert alert-danger' role='alert'>"
							+ "El producto ya imparte en esta categoria"
							+ "</div>";

					$('#aviso').html(aviso);
				}
			});

}