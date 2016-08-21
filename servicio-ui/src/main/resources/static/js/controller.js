angular.module('inicio')
	.controller('MenuCtrl',['$http','$route','$rootScope','$location', 
	                        function($http,$route,$rootScope,$location){
		
		var self = this;
		console.log('location path:' + window.location);
//		self.item = function(ruta){
//			return $route.current && ruta === $route.current.controller;
//		}

		var autenticar = function(credenciales){
			
			$http.get('/user')
			.then(function(response){
				if(response.data.name){
					$rootScope.nombreusuario = response.data.name;
					$rootScope.autenticado = true;
					$http.get('token').then(function(response){
						$http({
							url : '/carrito/cantidad',
							method : 'GET',
							headers : {
								'X-Auth-Token' : response.data.token
							}
						}).then(function(response){
							$rootScope.cantidad = response.data;
						});
					});
				}else{
					$rootScope.nombreusuario = null;
					$rootScope.autenticado = false;
				}
				//else $location.path('/recomendaciones');
			}, function(response){
				$rootScope.nombreusuario = null;
				$rootScope.autenticado = false;
				//else $location.path('/novedades');
			});
		};
		
		autenticar();
		
		self.logout = function(){
			$http.post('/logout', {})
			.finally(function() {
				$rootScope.nombreusuario = null;
				$rootScope.autenticado = false;
				$location.path("/catalogo");
			});
		};
	}]);

angular.module('catalogo')
	.controller('CatalogoCtrl',['$location', '$http' ,'$rootScope', 
	                            function($location, $http, $rootScope) {
		
		var self = this;
		
		var allCategorias = function(){
			$http.get('/categoria/all').then(function(response) {
				self.categorias = response.data;
			});
		}
		
		allCategorias();
		self.categorias = [];
		self.filtro = {};
		self.resultBusqueda = {};
		self.mostrarResult = false;
		self.mostrarDetalle = false;
		self.detalle = {};
		self.cantidad;
		self.error;
		self.msg;
		
		self.buscar = function(){
			$http.post('/busqueda/', self.filtro)
			.then(function(response){
				self.mostrarResult = true;
				self.mostrarDetalle = false;
				self.resultBusqueda = response.data;
			}, function(response){
				self.mostrarResult = false;
				self.mostrarDetalle = false;
				$rootScope.resultBusqueda = {};
				if(response.status == 404){
					self.msg = 'No hay resultados para la búsqueda';
				}else if(response.status == 400){
					self.error = 'Los filtros de la búsqueda no pueden estar vacios';
				}
			});
		};
		
		self.detalleLibro = function(id){
			$http.get('/catalogo/'+id)
			.then(function(response){
				self.mostrarResult = false;
				self.mostrarDetalle = true;
				self.detalle = response.data;
			});
		};
		self.volver = function(){
			self.mostrarResult = true;
			self.mostrarDetalle = false;
			self.detalle = {};
		};
		self.addCarrito = function(){
			var carrito = {
				id : self.detalle.id,
				titulo: self.detalle.titulo,
				precio: self.detalle.precio,
				cantidad : self.cantidad
			};
			$http.get('token').then(function(response){
				$http({
					url : '/carrito/',
					method : 'PUT',
					headers : {
						'X-Auth-Token' : response.data.token
					},
					data : carrito
				}).then(function(response){
					$rootScope.cantidad = response.data;
				});
			});
		};
	}]);
angular.module('cuenta')
	.controller('CuentaCtrl',['$http','$rootScope', '$location', 
	                                 function($http, $rootScope, $location) {
		var self = this;
		
		self.cuenta = {};
		self.password;
		self.cuenta.msg;
		self.cuenta.error;
		self.password_msg;
		self.password_error;
		self.repitapassword;
		
		$http.get('token').then(function(response){
			$http({
				url : '/cliente/' + $rootScope.nombreusuario,
				method : 'GET',
				headers : {
					'X-Auth-Token' : response.data.token
				}
			}).then(function(response){
				self.cuenta = response.data;
			}, function(){
				$rootScope.autenticado = false;
				$location.path('/login');
			});
		});
//		$http.get('http://localhost:8083/cliente/' + $rootScope.nombreusuario)
//		.then(function(response){
//			self.cuenta = response.data;
//		}, function(){
//			$rootScope.autenticado = false;
//			$location.path('/login');
//		});
		
		self.actualizarCuenta = function(){
			//limpiarCampos();
			$http.get('token').then(function(response){
				$http({
					url : '/cliente/',
					method : 'PUT',
					headers : {
						'X-Auth-Token' : response.data.token
					},
					data : self.cuenta
				}).then(function(){
					self.cuenta.msg = 'Cuenta actualizada correctamente';
				}, function(response){
					self.cuenta.error = response.data;
				});
			});
		};
		
		self.cambiarPassword = function(){
			//limpiarCampos();
			if(self.password.trim().length == 0){
				self.password_error = 'Debe indicar una nueva contraseña';
				return;
			}
			if(self.password != self.repitapassword){
				self.password_error = 'Las contraseñas no coinciden';
				return;
			}
			self.password_error=null;
			$http.put('/password', {password : self.password})
			.then(function(){
				self.password_msg = 'Contraseña actualizada correctamente';
				self.password = null;
				self.repitapassword=null;
				
			}, function(response){
				self.password_error = response.data; 
			});
		};
//		var limpiarCampos(){
//			self.cuenta.msg = null;
//			self.cuenta.error = null;
//			self.password = null;
//			self.repitapassword = null;
//			self.password_msg = null;
//			self.password_error = null;
//		}
	}]);
angular.module('carrito')
	.controller('CarritoCtrl',['$http','$location','$rootScope',
	                           function($http, $location, $rootScope){
		
		var self = this;
		
		self.items = [];
		self.total = 0;
		var calculoTotal = function(){
			for(var i = 0; i < self.items.length; i++) {
				self.total += (self.items[i].cantidad * self.items[i].precio);
			};
		};

		$http.get('token').then(function(response){
			$http({
				url : '/carrito/',
				method : 'GET',
				headers : {
					'X-Auth-Token' : response.data.token
				}
			}).then(function(response){
				self.items = response.data;
				calculoTotal();
			});
		});
		
		self.eliminar = function(id){
			$http.get('token').then(function(response){
				$http({
					url : '/carrito/' + id,
					method : 'DELETE',
					headers : {
						'X-Auth-Token' : response.data.token
					}
				}).then(function(response){
					self.items = response.data;
					$rootScope.cantidad = self.items.length;
					if($rootScope.cantidad === 0){
						$location.path('/catalogo');
					}else{
						calculoTotal();
					}
				});
			});
		};
		
	}]);