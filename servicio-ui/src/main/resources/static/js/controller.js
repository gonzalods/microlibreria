angular.module('inicio')
	.controller('MenuCtrl',['$http','$route','$rootScope','$location', 
	                        function($http,$route,$rootScope,$location){
		
		var self = this;
		self.item = function(ruta){
			return $route.current && ruta === $route.current.controller;
		}
		self.acceso = function(){
			return $route.current && $route.current.loadedTemplateUrl === 'fragments/login.html';
		}
		var autenticar = function(credenciales, callback){
			
			var headers = credenciales ? {
					authorization : "Basic " + btoa(credenciales.nombreusuario + ":" + 
													credenciales.password)
			}: {};
			
			$http.get('/user', {headers : headers})
			.then(function(response){
				if(response.data.name){
					$rootScope.nombreusuario = response.data.name;
					$rootScope.autenticado = true;
				}else{
					$rootScope.nombreusuario = null;
					$rootScope.autenticado = false;
				}
				callback && callback($rootScope.autenticado);
				//else $location.path('/recomendaciones');
			}, function(response){
				$rootScope.nombreusuario = null;
				$rootScope.autenticado = false;
				callback && callback($rootScope.autenticado);
				//else $location.path('/novedades');
			});
		};
		
		autenticar();
		self.credenciales = {};
			
		self.login = function(){
			autenticar(self.credenciales, function(autenticado) {
				if(autenticado){
					$location.path("/catalogo");
					$rootScope.authenticated = true;
					self.error =  false;
				}else{
					$location.path("/login");
					$rootScope.authenticated = false;
					self.error =  true;
				}
			});
		};
		
		self.logout = function(){
			$http.post('logout', {})
			.finally(function() {
				$rootScope.nombreusuario = null;
				$rootScope.autenticado = false;
				$location.path("/catalogo");
			});
		};
	}]);

angular.module('catalogo')
	.controller('CatalogoCtrl',['$location', '$http' ,function($location, $http) {
		
		var self = this;
		
		var allCategorias = function(){
			$http.get('http://localhost:8082/categoria/all').then(function(response) {
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
		self.error;
		self.msg;
		
		self.buscar = function(){
			$http.post('http://localhost:8081/busqueda', self.filtro)
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
			$http.get('http://localhost:8082/catalogo/'+id)
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
		}
	}]);
angular.module('cuenta')
	.controller('CuentaCtrl',['$http','$rootScope', '$location', 
	                                 function($http, $rootScope, $location) {
		var self = this;
		
		self.cuenta = {};
		self.password = {};
		self.cuenta.msg;
		self.cuenta.error;
		self.password.msg;
		self.password.error;
		
		$http.get('http://localhost:8083/cliente/' + $rootScope.nombreusuario)
		.then(function(response){
			self.cuenta = response.data;
		}, function(){
			$rootScope.autenticado = false;
			$location.path('/login');
		});
		
		self.actualizarCuenta = function(){
			$http.put('http://localhost:8083/cliente', self.cuenta)
			.then(function(){
				self.cuenta.msg = 'Cuenta actualizada correctamente';
			}, function(response){
				self.cuenta.error = response.data;
			})
		};
		
		self.cambiarPassword = function(){
			$http.put('password', self.password)
			.then(function(){
				self.password.msg = 'Contraseña actualizada correctamente';
			}, function(response){
				self.password.error = response.data; 
			});
		}
	}]);