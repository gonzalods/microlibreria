angular.module('app',['ngRoute','inicio','catalogo','cliente'])
	.config(function($routeProvider, $httpProvider) {
		$routeProvider.when('/inicio', { templateUrl: 'fragments/inicio.html'});
	    $routeProvider.when('/catalogo', { 
	    	templateUrl: 'fragments/catalogo.html', 
	    	controller: 'BusquedaCtrl',
	    	controllerAs: 'busqueda'});
	    $routeProvider.when('/iniciocliente', { 
	    	templateUrl: 'fragments/iniciocliente.html'//,
	    	//controller: 'InicioClienteCtrl',
	    	/*controllerAs: "iniciocliente"*/});
	    $routeProvider.when('/login', { 
	    	templateUrl: 'fragments/login.html',
	    	controller: 'InicioClienteCtrl',
	    	controllerAs: "iniciocliente"});
	    $routeProvider.otherwise({ redirectTo: '/inicio' });
	    
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	});
angular.module('inicio',[]);
angular.module('catalogo',[]);
angular.module('cliente',[]);
