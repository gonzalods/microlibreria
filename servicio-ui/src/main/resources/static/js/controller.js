angular.module('inicio')
	.controller('InicioCtrl', ['$scope', '$location', function($scope, $location) {
		
	}]);
angular.module('catalogo')
	.controller('NavAnonimoCtrl',['$scope', '$location', '$http' ,function($scope, $location, $http) {
		$http.get('http://localhost:8082/categoria/all').then(function(response) {
			console.log('hola');
			console.log(response);
			console.log(response.data);
			$scope.categorias = response.data;
		});
	}]);