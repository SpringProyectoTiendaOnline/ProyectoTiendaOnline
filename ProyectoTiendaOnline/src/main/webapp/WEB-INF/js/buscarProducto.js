
function buscarProducto() {
	var valor = document.getEmelentById("valorProducto").value;
	$.ajax({
		url : 'list-producto.html',
		dataType : 'json',
		type : 'GET',
		success : function(list) {
			$.each(list, function(i) {
				$("#tab").append( "<tr>" + 
						"<td>" + lista[i].titulo + "</td>" + 
					"</tr>");
			})
		}
	});


}