angular.module('inicio')
	.controller('InicioCtrl', ['$scope', '$location', function($scope, $location) {
		
	}]);
angular.module('catalogo')
	.controller('BusquedaCtrl',['$location', '$http' ,function($location, $http) {
		
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
			self.detalle = null;
			self.msg = null;
			self.error = null;
			$http.post('http://localhost:8081/busqueda', self.filtro)
			.then(function(response){
				self.mostrarResult = true;
				self.mostrarDetalle = false;
				self.resultBusqueda = response.data;
			}, function(response){
				self.mostrarResult = false;
				self.mostrarDetalle = false;
				self.resultBusqueda = {};
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
				console.log(response.data);
			});
		};
		self.volver = function(){
			self.mostrarResult = true;
			self.mostrarDetalle = false;
			self.detalle = {};
		}
	}]);
angular.module('cliente')
	.controller('InicioClienteCtrl',['$http','$rootScope', '$location', 
	                                 function($html, $rootScope, $location) {
		
		var self = this;
	
		if(!$rootScope.inicio){
			$location.path('/login');
			$rootScope.inicio = true;
		}
			
		var autenticate = function(credenciales, callback){
			if(credenciales.nombreusuario){
				
			}else{
				$location = '/login';
				$rootScope.autenticated = false;
			}
		};
		
//		autenticate();
		self.credenciales = {};
		var login = function(){
			autenticate(self.credenciales, function(autenticated){
				if(autenticate){
					$rootScope.autenticated = true;
				}
			});
		}
	}]);