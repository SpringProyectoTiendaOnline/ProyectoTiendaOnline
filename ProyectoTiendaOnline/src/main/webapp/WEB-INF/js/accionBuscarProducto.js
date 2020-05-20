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
	buscarProducto(producto);
});

// buttonBuscar

function buscarProducto(productoDto) {
	// token de html
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		// hace la petición AJAX y establece los datos en la cabecera
		xhr.setRequestHeader(header, token);
	});// ajaxSend

	window.location.href = "http://localhost:8080/ProyectoTiendaOnline/Producto/perfil-producto/"
			+ productoDto.idProducto;
	$('#buscarProducto').val("");

};
