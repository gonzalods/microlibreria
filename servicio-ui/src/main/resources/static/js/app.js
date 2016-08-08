angular.module('app',['ngRoute','inicio','catalogo','cuenta'])
	.config(function($routeProvider, $httpProvider) {
	    $routeProvider.when('/catalogo', { 
	    	templateUrl: 'fragments/catalogo.html', 
	    	controller: 'CatalogoCtrl',
	    	controllerAs: 'catalogo'});
	    $routeProvider.when('/login', { 
	    	templateUrl: 'fragments/login.html',
	    	controller: 'MenuCtrl',
	    	controllerAs: "menu"});
//	    $routeProvider.when('/novedades',{
//	    	templateUrl: 'fragments/novedades.html',
//	    	controller: 'CatalogoCtrl',
//	    	controllerAs: 'catalogo'});
//	    $routeProvider.when('/recomendaciones',{
//	    	templateUrl: 'fragments/recomendaciones.html',
//	    	controller: 'CatalogoCtrl',
//	    	controllerAs: 'catalogo'});
	    $routeProvider.when('/cuenta', { 
	    	templateUrl: 'fragments/cuenta.html',
	    	controller: 'CuentaCtrl',
	    	controllerAs: "cuenta"});
	    $routeProvider.otherwise({ redirectTo: '/catalogo' });
	    
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	});
angular.module('inicio',[]);
angular.module('catalogo',[]);
angular.module('cuenta',[]);
