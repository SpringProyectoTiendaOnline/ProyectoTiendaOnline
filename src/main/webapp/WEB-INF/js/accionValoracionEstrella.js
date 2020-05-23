class StarRating extends HTMLElement {
    get value () {
        return this.getAttribute('value') || 0;
    }

    set value (val) {
        this.setAttribute('value', val);
        this.highlight(this.value - 1);
    }

    get number () {
        return this.getAttribute('number') || 5;
    }

    set number (val) {
        this.setAttribute('number', val);

        this.stars = [];

        while (this.firstChild) {
            this.removeChild(this.firstChild);
        }

        for (let i = 0; i < this.number; i++) {
            let s = document.createElement('div');
            s.className = 'star';
            this.appendChild(s);
            this.stars.push(s);
        }

        this.value = this.value;
    }

    highlight (index) {
        this.stars.forEach((star, i) => {
            star.classList.toggle('full', i <= index);
        });
    }

    constructor () {
        super();

        this.number = this.number;

        this.addEventListener('mousemove', e => {
            let box = this.getBoundingClientRect(),
                starIndex = Math.floor((e.pageX - box.left) / box.width * this.stars.length);

            this.highlight(starIndex);
        });

        this.addEventListener('mouseout', () => {
            this.value = this.value;
        });

        this.addEventListener('click', e => {
            let box = this.getBoundingClientRect(),
                starIndex = Math.floor((e.pageX - box.left) / box.width * this.stars.length);

            this.value = starIndex + 1;

            let rateEvent = new Event('rate');
            this.dispatchEvent(rateEvent);
            
            agregarValoracion(this.value);
        });
    }
}

customElements.define('x-star-rating', StarRating);


function agregarValoracion (puntuacion){
	
	var idProducto = document.getElementById('idProducto').value;
	

	var token = $("meta[name='_csrf']").attr("content");
	  var header = $("meta[name='_csrf_header']").attr("content");
	  $(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	  });
	
	$.ajax({
      url: "http://localhost:8080/ProyectoTiendaOnline/Producto/agregarvaloracion/"+idProducto+"/"+puntuacion,
	  cache : false,
      contentType: "application/json; charset=utf-8",
      type: "POST",
	  
      success: function (response) {
   	  
          $('#aviso').html("");

    	 var aviso2 ="" +"<div class='alert alert-primary' role='alert'>"+ "Gracias por valorarlo !!"+ "</div>" + "";
      	
      	$('#aviso').html(aviso2);

        $('#aviso').html("");

    	
		},
		error: function(xhr, status, error) {
			
	      	$('#aviso').html("");

			 var aviso ="" +
		  		"<div class='alert alert-danger' role='alert'>"+
		  		"No se ha valorado, es probable que ya hayas valorado este producto antes. Prueba con otro producto"+
		  			"</div>" +
		  			"";
			 
		      	$('#aviso').html(aviso);


			
		}
		
		

  });
	
	
	 

	
}