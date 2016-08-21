angular.module('app',['ngRoute','inicio','catalogo','cuenta','carrito'])
	.config(function($routeProvider, $httpProvider) {
	    $routeProvider.when('/catalogo', { 
	    	templateUrl: '/ui/fragments/catalogo.html', 
	    	controller: 'CatalogoCtrl',
	    	controllerAs: 'catalogo'});
//	    $routeProvider.when('/login', { 
//	    	templateUrl: '/ui/fragments/login.html',
//	    	controller: 'MenuCtrl',
//	    	controllerAs: "menu"});
//	    $routeProvider.when('/novedades',{
//	    	templateUrl: 'fragments/novedades.html',
//	    	controller: 'CatalogoCtrl',
//	    	controllerAs: 'catalogo'});
//	    $routeProvider.when('/recomendaciones',{
//	    	templateUrl: 'fragments/recomendaciones.html',
//	    	controller: 'CatalogoCtrl',
//	    	controllerAs: 'catalogo'});
	    $routeProvider.when('/cuenta', { 
	    	templateUrl: '/ui/fragments/cuenta.html',
	    	controller: 'CuentaCtrl',
	    	controllerAs: "cuenta"});
	    $routeProvider.when('/verCarrito', {
	    	templateUrl: '/ui/fragments/carrito.html',
	    	controller : 'CarritoCtrl',
	    	controllerAs: "carrito"});
	    $routeProvider.when('/cuentanueva',{redirectTo: '/cuenta'});
	    $routeProvider.otherwise({ redirectTo: '/catalogo' });
	    
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	});
angular.module('inicio',[]);
angular.module('catalogo',['ngCookies','csrf-cross-domain'])
.config(function(csrfCDProvider){
	csrfCDProvider.setAllowedMethods(['PUT','DELETE']);
});
angular.module('cuenta',['ngCookies','csrf-cross-domain'])
	.config(function(csrfCDProvider){
		csrfCDProvider.setAllowedMethods(['PUT']);
	});
angular.module('carrito',['ngCookies','csrf-cross-domain'])
	.config(function(csrfCDProvider){
		csrfCDProvider.setAllowedMethods(['PUT','DELETE']);
	});