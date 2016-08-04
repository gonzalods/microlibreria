angular.module('app',['ngRoute','inicio','catalogo','cliente'])
	.config(function($routeProvider, $httpProvider) {
		$routeProvider.when('/inicio', { templateUrl: 'fragments/inicio.html'/*, controller: 'InicioCtrl'*/});
	    $routeProvider.when('/catalogo', { templateUrl: 'fragments/catalogo.html', controller: 'NavAnonimoCtrl'});
	    //$routeProvider.when('/finish', { templateUrl: 'partials/finish.html' });
	    $routeProvider.otherwise({ redirectTo: '/inicio' });
	    
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	});
angular.module('inicio',[]);
angular.module('catalogo',[]);
angular.module('cliente',[]);
