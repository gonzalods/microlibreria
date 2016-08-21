$(document).ready(function(){
	var $bt_anadir = $('#anadir');
	var $bt_modificar = $('#modificar');
	var $bt_eliminar = $('#eliminar');
	var $input_autor = $('#autor');
	var $sel_autores = $('#autores');
	var $form_detalle = $('#formdetalle');
	var $link_volver = $('#volver');
	var $form_volver = $('#formvolver');
	var $hidden_in_input_autor;
	var $optionsSel;
	if($bt_anadir){
		$bt_anadir.click(function(evt){
			anadirAutor();
			evt.preventDefault();
		});
		$bt_modificar.click(function(evt){
			modificarAutor();
			evt.preventDefault();
		});
		$bt_eliminar.click(function(evt){
			elminarAutores();
			evt.preventDefault();
		});
		$link_volver.click(function (evt){
			$form_volver.submit();
			evt.preventDefault();
		});
		$sel_autores.change(function(){
			var optsSelected = $('#autores option:selected');
			if(optsSelected.length == 0){
				$bt_modificar.attr('disabled', true);
				$bt_eliminar.attr('disabled', true);
			}else if(optsSelected.length > 1){
				$optionsSel =  optsSelected;
				$bt_modificar.attr('disabled', true);
				$input_autor.val('');
			}else if(optsSelected.length == 1){
				$optionsSel =  optsSelected;
				$bt_modificar.attr('disabled', false);
				$bt_eliminar.attr('disabled', false);
				$input_autor.val(optsSelected.val());
				$hidden_in_input_autor = $("input[value='"+ optsSelected.val() + "']");
			}
		})
		
	};
	
	var anadirAutor = function(){
		var nomAutor = $input_autor.val().trim();
		if(nomAutor.length != 0){
			$('<option/>').attr('value',nomAutor).text(nomAutor).appendTo($sel_autores);
			$('<input/>').attr('type','hidden')
				.attr('name','autores').attr('value',nomAutor).appendTo($form_detalle);
		}
	};
	
	var modificarAutor = function(){
		var nomAutor = $input_autor.val();
		$hidden_in_input_autor.val(nomAutor);
		$optionsSel.val(nomAutor).text(nomAutor);
	};
	
	var elminarAutores = function(){
		$optionsSel.each(function(){
			var nomAutor = $(this).val();
			$("input[value='"+ nomAutor + "']").detach();
			$(this).detach();
			
		});
	};

});