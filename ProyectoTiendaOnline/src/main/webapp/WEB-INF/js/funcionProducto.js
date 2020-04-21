//ajax submit data.

function uploadImagen(obj) {
	var fileName = obj.files[0];
	var formData = new FormData();
	formData.append('pic', fileName);
	
	var imagen = document.getElementById("imagenFile");
	if (imagen.value != "") {
		$.ajax({
					type : "POST",
					url : "/Producto/uploadFile",
					contentType : false,
					processData : false,
					cache : false,
					data : formData,
					success : function(response) {
						if (response != null) {
							console.log("Response " + response.imagen);
							result = '<img src="' + response.imagen
									+ '" width="100" height="100">';
							$('#result').html(result);

						}

					},
					error : function() {
						console.log("上传失败");
					},
				});
	} else {
		console.log("请先选择文件");
	}
};

