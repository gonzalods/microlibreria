angular.module('inicio')
	.controller('InicioCtrl', ['$scope', '$location', function($scope, $location) {
		
	}]);
angular.module('catalogo')
	.controller('NavAnonimoCtrl',['$scope', '$location', '$http' ,function($scope, $location, $http) {
		$http.get('categorias', function(response) {
			$scope.categorias = response.data.categorias;
		});
	}]);